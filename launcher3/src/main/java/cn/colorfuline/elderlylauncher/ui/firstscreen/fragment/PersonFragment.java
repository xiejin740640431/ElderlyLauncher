package cn.colorfuline.elderlylauncher.ui.firstscreen.fragment;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.colorfuline.base.utils.DateUtil;
import cn.colorfuline.base.utils.DialogUtil;
import cn.colorfuline.base.utils.ResponseCode;
import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.base.widget.util.SpaceItemDecoration;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.EmergencyContactCardInfo;
import cn.colorfuline.elderlylauncher.bean.ServiceBean;
import cn.colorfuline.elderlylauncher.bean.UserBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.bean.request.GetEmConCard;
import cn.colorfuline.elderlylauncher.https.RetrofitFamilyUtil;
import cn.colorfuline.elderlylauncher.https.imageLoader.ImageLoader;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import cn.colorfuline.elderlylauncher.ui.firstscreen.PhoneUtil;
import cn.colorfuline.elderlylauncher.ui.firstscreen.UserHelper;
import cn.colorfuline.elderlylauncher.ui.firstscreen.adapter.FamilyAdapter;
import cn.colorfuline.elderlylauncher.ui.firstscreen.db.MyProviderMetaData;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;
import cn.colorfuline.elderlylauncher.utils.AppUtils;
import cn.colorfuline.elderlylauncher.widget.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonFragment extends BaseFirstScreenFragment implements OnItemClickListener,View.OnClickListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.user_image)
    CircleImageView civ;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_sign)
    TextView tv_sign;
    @BindView(R.id.tv_person_info)
    TextView tv_person_info;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_edit)
    TextView tv_edit;

    private ArrayList<ServiceBean> serviceList = new ArrayList<>();
    final int SPACE = 2;
    private int SPANCOUNT = 3;
    private FamilyAdapter familyAdapter;
    private MyObserver observer;
    private UserBean ub;

    @Override
    public int getLayout() {return R.layout.fragment_person;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=super.onCreateView(inflater,container,savedInstanceState);
        ButterKnife.bind(this,rootview);
        return rootview;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dealTopData();
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewStyle();
        initData();
        setAdapter();
        registerContentProviderObserver();
        dealTopData();
    }
    private void dealTopData(){
        ub=queryContentProviderData();
        UserHelper.getInstance().setUserBean(ub);
        try {
            if(ub!=null){
                tv_name.setVisibility(View.VISIBLE);
                tv_sign.setVisibility(View.VISIBLE);
                tv_person_info.setVisibility(View.VISIBLE);
                tv_edit.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.GONE);
                ImageLoader.getInstance().load(getActivity(),ub.getUserAvatar(),R.mipmap.head_default,civ);
                tv_name.setText(ub.getUserName());
                tv_sign.setText(TextUtils.isEmpty(ub.getNickName())?"他的个性签名什么的，还没写哦!":ub.getNickName());
                String sexName=TextUtils.isEmpty(ub.getUserSex())?"":ub.getUserSex().equals("1")?"男":"女";
                String age=TextUtils.isEmpty(ub.getUserBirthday())?"": DateUtil.getAge(ub.getUserBirthday())+"";
                tv_person_info.setText(sexName+" "+age+" "+ub.getUserAddr());//男 62岁 广东深圳
            }else{
                civ.setImageResource(R.mipmap.head_default);
                tv_name.setVisibility(View.GONE);
                tv_sign.setVisibility(View.GONE);
                tv_person_info.setVisibility(View.GONE);
                tv_edit.setVisibility(View.GONE);
                tv_login.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserBean queryContentProviderData() {
        ContentResolver recolver=getActivity().getContentResolver();
        UserBean userBean=null;
        Cursor cursor=recolver.query(MyProviderMetaData.UserTableMetaData.CONTENT_URI,null,null,null,null);
        if(cursor!=null) {
            if (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_NAME));
                String password = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_PW));
                String nickname = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.NICK_NAME));
                String avatar = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_AVATAR));
                String sex = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_SEX));
                String birthday = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_BIRTHDAY));
                String address = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_ADDR));
                String uid = cursor.getString(cursor.getColumnIndex(MyProviderMetaData.UserTableMetaData.USER_ID));
                userBean = new UserBean(username, password, nickname, avatar, sex, birthday, address,uid);
            }
            cursor.close(); cursor=null;
        }
        return userBean;
    }

    private void registerContentProviderObserver() {
        ContentResolver resolver=getActivity().getContentResolver();
        observer=new MyObserver(handler);
        resolver.registerContentObserver(MyProviderMetaData.UserTableMetaData.INSERT_URI,false,observer);
        resolver.registerContentObserver(MyProviderMetaData.UserTableMetaData.DELETE_URI,false,observer);
        resolver.registerContentObserver(MyProviderMetaData.UserTableMetaData.UPDATE_URI,false,observer);
    }

    private void initData() {
        serviceList.add(new ServiceBean("紧急联系卡", R.mipmap.jinjilianxika, null, null));
        serviceList.add(new ServiceBean("一键求助", R.mipmap.yijianqiuzhu, null, null));
        serviceList.add(new ServiceBean("时间提醒", R.mipmap.shijiantixing, null, null));
    }

    private void initViewStyle() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPANCOUNT));
        recyclerView.addItemDecoration(new SpaceItemDecoration(SPACE));
    }
    private void setAdapter() {
        if (familyAdapter == null) {
            familyAdapter = new FamilyAdapter(recyclerView, serviceList, (getResources().getDisplayMetrics().widthPixels - SPACE * 2 * SPANCOUNT) / SPANCOUNT);
            familyAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(familyAdapter);
        } else {
            familyAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 紧急联系卡
     */
    private EmergencyContactCardInfo cardInfo;

    @Override
    public void onItemClick(View v, int position) {
        ServiceBean bean = serviceList.get(position);
        Intent intent=new Intent();
        if(!AppUtils.isAvilible(getActivity(),"com.qicaihong")){
            showDownloadDialog();
            return;
        }
        UserBean beans=UserHelper.getInstance().getUserBean();
        if(UserHelper.getInstance().getUserBean()==null){
            CustomToast.showShort(getActivity(),"请先登录哦");
            ComponentName cn2=new ComponentName("com.qicaihong",
                    "cn.colorfuline.parents.ui.LoginActivity");
            intent.setComponent(cn2);
            startActivity(intent);
            return ;
        }
//        String packageName="com.qicaihong";//父母版的applicationId
//        if(UserHelper.getInstance().getUserBean()!=null&&AppUtils.isBackgroundRunning(getActivity(),packageName)){
//
//        }
        if(!TextUtils.isEmpty(bean.getName())){
            if(bean.getName().equals("紧急联系卡")){
                ComponentName cn=new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.family.EmergencyContactActivity");
                intent.setComponent(cn);
                startActivity(intent);
            }else if(bean.getName().equals("一键求助")){
                if(ub==null){
                    CustomToast.showShort(getActivity(),"请先登录哦!");
                    return;
                }
                if (AppUtils.isCanUseSim(getActivity().getApplication())) {
                    if(cardInfo==null){
                        getEmConCardInfo();
                    }else{
                        PhoneUtil.callPhone(cardInfo.getContacts(),getActivity());
                    }
                } else {
                    CustomToast.showShort(getActivity(),"sim卡不可用");
                }
            }else if(bean.getName().equals("时间提醒")){
                ComponentName cn=new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.family.NewRemindActivity");
                intent.setComponent(cn);
                startActivity(intent);
            }
        }
    }
    @OnClick({R.id.tv_edit,R.id.tv_login})
    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.tv_edit:
                ComponentName cn1=new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.family.PersonalDataActivity");
                intent.setComponent(cn1);
                intent.putExtra("type_from_lanuch","type_from_lanuch");
                startActivity(intent);
                break;
            case R.id.tv_login:
                if(!AppUtils.isAvilible(getActivity(),"com.qicaihong")){
                    showDownloadDialog();
                    return;
                }
                ComponentName cn2=new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.LoginActivity");
                intent.setComponent(cn2);
                startActivity(intent);
                break;
        }
    }

    private class MyObserver extends ContentObserver{
        private final Handler handler;

        public MyObserver(Handler handler) {
            super(handler);
            this.handler=handler;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            handler.sendEmptyMessage(1);
        }
    }
    /**
     * 获取紧急联系卡信息
     */
    public void getEmConCardInfo() {
        DialogUtil.showDialog(getActivity(),"数据加载中...");
        String userId = ub.getUserId();
        RetrofitFamilyUtil.getInstance().getFamilyApiInstance().getEmConCardInfo(new Token<GetEmConCard>("", new GetEmConCard(userId))).enqueue(new Callback<JsonBean<EmergencyContactCardInfo>>() {
            @Override
            public void onResponse(Call<JsonBean<EmergencyContactCardInfo>> call, Response<JsonBean<EmergencyContactCardInfo>> response) {
                if (response != null && response.body() != null) {
                    if (ResponseCode.SUCCESS_STATUS.equals(response.body().getStatus())) {
                        //更新到全局application中
                        cardInfo =response.body().getResponse();
                        //非空判断
                        if (cardInfo!= null) {
                            //拨打电话
                            PhoneUtil.callPhone(cardInfo.getContacts(),getActivity());
                        } else {
                            CustomToast.showShort(getActivity(),"请先创建紧急联系卡");
                        }
                    } else {
                        CustomToast.showShort(getActivity(),TextUtils.isEmpty(response.body().getMessage()) ? getString(R.string.str_data_load_fail) : response.body().getMessage());
                    }
                } else {
                    CustomToast.showShort(getActivity(),getString(R.string.str_data_load_fail));
                }
                DialogUtil.dismissDialog();
            }

            @Override
            public void onFailure(Call<JsonBean<EmergencyContactCardInfo>> call, Throwable t) {
                CustomToast.showShort(getActivity(),getString(R.string.str_data_load_fail));
                DialogUtil.dismissDialog();
            }
        });
    }

    /**
     *
     */
    public void showDownloadDialog(){
        String title = "彩虹在线";
        showConfirmDialog("com.qicaihong",getResources().getString(R.string.str_download_confirm)+title,title,"http://10.80.84.236:8080/examples/apks/colorfuline_parent_V4.2.0_release.apk");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getContentResolver().unregisterContentObserver(observer);
    }
}

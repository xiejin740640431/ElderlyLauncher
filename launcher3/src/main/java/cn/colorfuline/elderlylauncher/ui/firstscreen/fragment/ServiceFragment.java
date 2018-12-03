package cn.colorfuline.elderlylauncher.ui.firstscreen.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colorfuline.library_swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.colorfuline.base.utils.ResponseCode;
import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.base.widget.util.SpaceItemDecoration;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.ServiceBean;
import cn.colorfuline.elderlylauncher.bean.ServiceTypeBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.bean.request.RequestServiceTypeBean;
import cn.colorfuline.elderlylauncher.config.Config;
import cn.colorfuline.elderlylauncher.https.RetrofitServiceUtil;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import cn.colorfuline.elderlylauncher.ui.firstscreen.UserHelper;
import cn.colorfuline.elderlylauncher.ui.firstscreen.adapter.FamilyAdapter;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;
import cn.colorfuline.elderlylauncher.utils.AppUtils;
import cn.colorfuline.elderlylauncher.widget.MRecyclerview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFirstScreenFragment implements OnItemClickListener {
    @BindView(R.id.recyclerView)
    MRecyclerview recyclerView;

    final int SPACE = 2;
    private int SPANCOUNT = 3;
    private FamilyAdapter familyAdapter;

    private ArrayList<ServiceBean> serviceList = new ArrayList<>();

    List<ServiceTypeBean> typeBeanList = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment_service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setAdapter();
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewStyle();
        setListener();
        getLookServiceType();//请求支持的服务类型
    }

    private void initViewStyle() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPANCOUNT));
        recyclerView.addItemDecoration(new SpaceItemDecoration(SPACE));
    }

    private void setAdapter() {
        if (familyAdapter == null) {
            familyAdapter = new FamilyAdapter(recyclerView.getRecyclerView(), serviceList, (getResources().getDisplayMetrics().widthPixels - SPACE * 2 * SPANCOUNT) / SPANCOUNT);
            familyAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(familyAdapter);
        } else {
            familyAdapter.notifyDataSetChanged();
        }
    }

    private void setListener() {
        recyclerView.setOnErrorBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.hideError();
                getLookServiceType();
            }
        });
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLookServiceType();
            }
        });
        recyclerView.setLoadMoreEnable(false);
    }

    @Override
    public void onItemClick(View v, int position) {
        try {
            ServiceBean bean = serviceList.get(position);
            Intent intent = new Intent();
            if (!AppUtils.isAvilible(getActivity(), "com.qicaihong")) {
                showDownloadDialog();
                return;
            }
            if (UserHelper.getInstance().getUserBean() == null) {
                CustomToast.showShort(getActivity(), "请先登录哦");
                ComponentName cn2 = new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.LoginActivity");
                intent.setComponent(cn2);
                startActivity(intent);
                return;
            }
            if (!TextUtils.isEmpty(bean.getTypeId())) {
                //参数是包名+类路径
                ComponentName cn = new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.service.activity.business.BusinessServiceActivity");
                intent.setComponent(cn);
                intent.putExtra("typeId", bean.getTypeId());
                startActivity(intent);
            } else if (bean.getName().equals("保险")) {
                ComponentName cn = new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.PubWebViewActivity");
                intent.setComponent(cn);
                intent.putExtra("title", "保险");
                intent.putExtra("url", Config.INSURANCE_URL);
                startActivity(intent);
            } else if (bean.getName().equals("直播")) {
                ComponentName cn = new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.function.LiveListActivity");
                intent.setComponent(cn);
                startActivity(intent);
            } else if (bean.getName().equals("志愿者")) {
                ComponentName cn = new ComponentName("com.qicaihong",
                        "cn.colorfuline.parents.ui.service.activity.volunteer.AroundVolunteerActivity");
                intent.setComponent(cn);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        serviceList.clear();
//        serviceList.add(new ServiceBean("资讯", R.mipmap.ser_information, null, null));
        if (typeBeanList != null && typeBeanList.size() > 0) {
            for (int i = 0; i < typeBeanList.size(); i++) {
                serviceList.add(new ServiceBean(typeBeanList.get(i).getTypeName(), R.mipmap.ser_housekeeping, null, typeBeanList.get(i).getId()));
            }
        }
        serviceList.add(new ServiceBean("保险", R.mipmap.ser_insurance, null, null));
        serviceList.add(new ServiceBean("直播", R.mipmap.ser_live, null, null));
        serviceList.add(new ServiceBean("志愿者", R.mipmap.ser_aroundvolunteer, null, null));
        handler.sendEmptyMessage(1);//通知刷新页面
    }

    /**
     * 获取服务类型
     */
    public void getLookServiceType() {
        RequestServiceTypeBean serviceTypeBean = new RequestServiceTypeBean("", "0");
        RetrofitServiceUtil.getInstance().getServiceApiInstance().getServiceType(new Token<RequestServiceTypeBean>("", serviceTypeBean)).enqueue(new Callback<JsonBean<List<ServiceTypeBean>>>() {
            @Override
            public void onResponse(Call<JsonBean<List<ServiceTypeBean>>> call, Response<JsonBean<List<ServiceTypeBean>>> response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    recyclerView.hideLoading();
                    recyclerView.getSwipeToLoadLayout().setRefreshing(false);
                    recyclerView.getSwipeToLoadLayout().setLoadingMore(false);
                    if (response != null && response.body() != null) {
                        if (ResponseCode.SUCCESS_STATUS.equals(response.body().getStatus()) && response.body().getResponse() != null) {
                            typeBeanList = response.body().getResponse();
                        }
                    } else {
                        CustomToast.showShort(getActivity(), "获取服务类型失败");
                    }
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<JsonBean<List<ServiceTypeBean>>> call, Throwable t) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    recyclerView.hideLoading();
                    recyclerView.getSwipeToLoadLayout().setRefreshing(false);
                    recyclerView.getSwipeToLoadLayout().setLoadingMore(false);
                    CustomToast.showShort(getActivity(), "获取服务类型失败");
                    loadData();
                }
            }

        });
    }

    /**
     *
     */
    public void showDownloadDialog() {
        String title = "彩虹在线";
        showConfirmDialog("com.qicaihong", getResources().getString(R.string.str_download_confirm) + title, title, "http://10.80.84.236:8080/examples/apks/colorfuline_parent_V4.2.0_release.apk");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

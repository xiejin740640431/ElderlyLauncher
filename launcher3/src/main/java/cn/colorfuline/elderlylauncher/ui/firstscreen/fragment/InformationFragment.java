package cn.colorfuline.elderlylauncher.ui.firstscreen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colorfuline.library_swipetoloadlayout.OnLoadMoreListener;
import com.colorfuline.library_swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.colorfuline.base.utils.ResponseCode;
import cn.colorfuline.base.utils.StringUtils;
import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.InformationBean;
import cn.colorfuline.elderlylauncher.bean.InformationDiggListBean;
import cn.colorfuline.elderlylauncher.bean.InformationParseBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.bean.request.RequestInformationBean;
import cn.colorfuline.elderlylauncher.config.Config;
import cn.colorfuline.elderlylauncher.https.RetrofitServiceUtil;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import cn.colorfuline.elderlylauncher.ui.firstscreen.InforDetailActivity;
import cn.colorfuline.elderlylauncher.ui.firstscreen.adapter.InforListAdapter;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;
import cn.colorfuline.elderlylauncher.widget.MRecyclerview;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends BaseFirstScreenFragment implements OnItemClickListener {
    @BindView(R.id.recycleview)
    MRecyclerview mRecyclerView;

    int pageNumber = Config.PAGE_NUMBER_START;
    private int pageCount; //返回的总页数

    //资讯列表信息
    List<InformationBean> list = new ArrayList<>();
    private InforListAdapter inforListAdapter;

    @Override
    public int getLayout() {  return R.layout.fragment_information;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this,rootView);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 1处理数据   2完成数据处理后刷新ui    3错误
            mRecyclerView.hideLoading();
            switch (msg.what){
                case 1:
                    JsonBean<InformationParseBean> response= (JsonBean<InformationParseBean>) msg.obj;
                    int isRefresh=msg.arg1;
                    if (ResponseCode.SUCCESS_STATUS.equals(response.getStatus())) {
                        List<InformationBean> infoList = response.getResponse().getList();
                        List<InformationDiggListBean> digglist=response.getResponse().getDiggList();
                        if (isRefresh==1) {
                            list.clear();
                        } else {
                            pageNumber++;
                        }
                        list.addAll(infoList);
                        pageCount = response.getResponse().getPageCount();
                    } else {
                        CustomToast.showShort(getActivity(),StringUtils.isEmpty(response.getMessage()) ? "资讯加载失败":response.getMessage());
                    }
                    setAdapter();
                    mRecyclerView.getSwipeToLoadLayout().setLoadingMore(false);
                    mRecyclerView.getSwipeToLoadLayout().setRefreshing(false);
                    if (pageNumber < pageCount) {
                        mRecyclerView.setLoadMoreEnable(true);
                    } else {
                        mRecyclerView.setLoadMoreEnable(false);
                    }
                    break;
                case 3:
                    mRecyclerView.getSwipeToLoadLayout().setLoadingMore(false);
                    mRecyclerView.getSwipeToLoadLayout().setRefreshing(false);
                    CustomToast.showShort(getActivity(),"资讯加载失败");
                    break;
            }
        }
    };

    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNumber++;
                if (pageNumber > pageCount) {
                    CustomToast.showShort(getActivity(),"没有更多内容了");
                    if(!getActivity().isFinishing()) {
                        mRecyclerView.getSwipeToLoadLayout().setLoadingMore(false);}
                    return;
                }
                getInfoList(false);
            }
        });
        mRecyclerView.setLoadMoreEnable(true);

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.getSwipeToLoadLayout().setRefreshing(true);
            }
        });
    }
    public void setAdapter() {
        if (inforListAdapter == null) {
            inforListAdapter = new InforListAdapter(list);
            inforListAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(inforListAdapter);
        } else {
            inforListAdapter.notifyDataSetChanged();
        }
    }

    private void refresh() {
        mRecyclerView.showLoading("");
        pageNumber = Config.PAGE_NUMBER_START;
        pageCount = 0;
        getInfoList(true);
    }

    private void getInfoList(final boolean isRefresh) {
        RequestInformationBean bean=new RequestInformationBean("","",pageNumber, Config.PAGE_SIZE);
        RetrofitServiceUtil.getInstance().getServiceApiInstance().getInformations(new Token<RequestInformationBean>("",bean))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JsonBean<InformationParseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(3);
                    }

                    @Override
                    public void onNext(JsonBean<InformationParseBean> response) {
                        Message msg=Message.obtain();
                        msg.what=1;
                        msg.obj=response;
                        msg.arg1=isRefresh?1:0;
                        handler.sendMessage(msg);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onItemClick(View v, int position) {
        if (inforListAdapter != null) {
            Intent intent=new Intent(getActivity(),InforDetailActivity.class);
            try {
                intent.putExtra("url",list.get(position).getReferenceUrl());
                intent.putExtra("contentId",list.get(position).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }
}

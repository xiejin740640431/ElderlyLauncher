package cn.colorfuline.elderlylauncher.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.colorfuline.library_swipetoloadlayout.OnLoadMoreListener;
import com.colorfuline.library_swipetoloadlayout.OnRefreshListener;
import com.colorfuline.library_swipetoloadlayout.SwipeToLoadLayout;

import cn.colorfuline.base.utils.StringUtils;
import cn.colorfuline.base.view.FButton;
import cn.colorfuline.base.view.RippleBackground;
import cn.colorfuline.elderlylauncher.R;


/**
 * Created by CQDXP on 2016/5/31.
 */
public class MRecyclerview extends FrameLayout {
    private View contentView;

    /**
     *
     */
    private RecyclerView recyclerView;
    /**
     *
     */
    private SwipeToLoadLayout swipeToLoadLayout;
    /**
     * loading布局
     */
    private View layout_loading;
    /**
     * 数据加载中
     */
    private TextView tv_loading;
    /**
     * loading动画
     */
    private RippleBackground ripple_loading;
    /**
     * 空数据布局
     */
    private View layout_emptyView;
    /**
     *
     */
    private TextView tv_emptyView;
    /**
     * 加载失败
     */
    private View layout_error;
    /**
     *
     */
    private FButton btn_error;
    /**
     * 适配器
     */
    private RecyclerView.Adapter mAdapter;
    /**
     * 观察者
     */
    private MAdapterDataObserver mAdapterDataObserver;

    public MRecyclerview(Context context) {
        super(context);
        init(context);
    }

    public MRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MRecyclerview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        addRecyclerView(context,layoutInflater);
        addEmptyView(context, layoutInflater);
        addErrorView(context, layoutInflater);
        addLoadingView(context, layoutInflater);
    }

    /**
     * 添加recyclerView
     */
    public void addRecyclerView(Context context, LayoutInflater layoutInflater) {
        contentView = layoutInflater.inflate(R.layout.layout_recyclerview, null);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) contentView.findViewById(R.id.swipeToLoadLayout);
        addView(contentView);
        contentView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
        contentView.getLayoutParams().width = LayoutParams.MATCH_PARENT;
    }

    /**
     * @param context
     * @param layoutInflater
     */
    public void addLoadingView(Context context, LayoutInflater layoutInflater) {
        layout_loading = layoutInflater.inflate(R.layout.layout_recycleview_loading, null);
        addView(layout_loading);
        tv_loading = (TextView) layout_loading.findViewById(R.id.tv_loading);
        ripple_loading = (RippleBackground) layout_loading.findViewById(R.id.ripple_loading);
        layout_loading.setVisibility(View.GONE);
    }

    /**
     * 设置emptyView
     *
     * @param context
     * @param layoutInflater
     */
    public void addEmptyView(Context context, LayoutInflater layoutInflater) {
        layout_emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null);
        addView(layout_emptyView);
        tv_emptyView = (TextView) layout_emptyView.findViewById(R.id.tv_empty_view);
        layout_emptyView.setVisibility(View.GONE);
    }

    /**
     * 设置emptyView
     *
     * @param context
     * @param layoutInflater
     */
    public void addErrorView(Context context, LayoutInflater layoutInflater) {
        layout_error = layoutInflater.inflate(R.layout.layout_error_recycleview, null);
        addView(layout_error);
        btn_error = (FButton) layout_error.findViewById(R.id.btn_error);
        layout_error.setVisibility(View.GONE);
    }

    public void setOnErrorBtnClick(OnClickListener btnErrorClick) {
        btn_error.setOnClickListener(btnErrorClick);
    }

    /**
     * 获取recyclerview
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     *
     * @return
     */
    public SwipeToLoadLayout getSwipeToLoadLayout(){
        return swipeToLoadLayout;
    }
    /**
     * 设置是否刷新
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable){
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
    }

    /**
     * 设置是否加载更多
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable){
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    /**
     * 设置刷新监听
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        swipeToLoadLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置加载更多监听
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        swipeToLoadLayout.setOnLoadMoreListener(onLoadMoreListener);
    }

    /**
     * 设置数据源适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mAdapter != null && mAdapterDataObserver != null) {
            mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        mAdapter = adapter;
        recyclerView.setAdapter(adapter);
        if (mAdapter != null) {
            mAdapterDataObserver = new MAdapterDataObserver();
            mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
    }

    /**
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * @param decor
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    /**
     * 显示empryView
     */
    public void showEmptyView() {
        if (layout_emptyView != null) {
            layout_emptyView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (layout_emptyView != null) {
            layout_emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示loading
     *
     * @param loadingStr
     */
    public void showLoading(String loadingStr) {
        if (!StringUtils.isEmpty(loadingStr)) {
            tv_loading.setText(loadingStr);
        }
        ripple_loading.startRippleAnimation();
        layout_loading.setVisibility(View.VISIBLE);
        hideError();
        hideEmptyView();
    }

    public void hideLoading() {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * @param errorStr
     */
    public void showError(String errorStr) {
        if (!StringUtils.isEmpty(errorStr)) {
            btn_error.setText(errorStr);
        }
        layout_error.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏error
     */
    public void hideError() {
        layout_error.setVisibility(View.GONE);
    }

    /**
     *
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (ripple_loading != null) {
                        ripple_loading.stopRippleAnimation();
                    }
                    layout_loading.setVisibility(View.GONE);
                    break;
            }
        }
    };
    /**
     * 显示内容布局
     */
    public void showContent(){
        if(contentView!=null){
            contentView.setVisibility(View.VISIBLE);
        }
        hideError();
    }

    /**
     * 隐藏
     */
    public void hideContent(){
        if(contentView!=null){
            contentView.setVisibility(View.GONE);
        }
    }
    /**
     *
     */
    class MAdapterDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            updateUI();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            updateUI();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            updateUI();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            updateUI();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            updateUI();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            updateUI();
        }

        public void updateUI() {
            if (recyclerView.getAdapter().getItemCount() == 0) {
                showEmptyView();
            } else {
                hideEmptyView();
            }
        }
    }
}

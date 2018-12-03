package cn.colorfuline.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.colorfuline.base.R;
import cn.colorfuline.base.widget.LoadingView;


/**
 * Created by CQDXP on 2016/7/7.
 */
public abstract class CFBaseLoadingFragment extends CFBaseFragment {
    /**
     * 滑动
     */
    LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        loadingView = (LoadingView) view.findViewById(R.id.layout_loading);
        if (getLoadingContainerViewId() > 0 && loadingView != null) {
            loadingView.setContentView(inflater.inflate(getLoadingContainerViewId(), null));
            loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorClick();
                }
            });
        }
        return view;
    }

    @Override
    protected int getContainerViewId() {
        return R.layout.activity_base_loading;
    }

    /**
     * 获取布局资源文件id
     *
     * @return
     */
    protected abstract int getLoadingContainerViewId();

    /**
     * error点击重新加载
     */
    protected abstract void onErrorClick();

    /**
     * 显示loading
     *
     * @param loadingStr
     */
    public void showLoading(CharSequence loadingStr) {
        loadingView.showLoading(loadingStr);
    }

    /**
     * 显示error
     *
     * @param errorStr
     */
    public void showError(CharSequence errorStr) {
        loadingView.showError(errorStr);
    }

    /**
     * 显示内容
     */
    public void showContent() {
        loadingView.showContent();
    }
}

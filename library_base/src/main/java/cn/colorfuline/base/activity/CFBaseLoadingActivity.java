package cn.colorfuline.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.colorfuline.base.R;
import cn.colorfuline.base.widget.LoadingView;


/**
 * Created by CQDXP on 2016/6/28.
 */
public abstract class CFBaseLoadingActivity extends CFBaseActivity {
    /**
     * 滑动
     */
    LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingView = (LoadingView) findViewById(R.id.layout_loading);
        if (getLoadingContainerViewId() > 0 && loadingView != null) {
            loadingView.setContentView(getLayoutInflater().inflate(getLoadingContainerViewId(), null));
            loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorClick();
                }
            });
        }
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

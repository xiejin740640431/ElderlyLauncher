package cn.colorfuline.base.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.colorfuline.base.R;
import cn.colorfuline.base.spinkit.SpinKitView;


/**
 * Created by CQDXP on 2016/7/2.
 */
public class LoadingView extends FrameLayout {
    /**
     * loading布局
     */
    private View layout_loading;
    /**
     *
     */
    private SpinKitView spinKitView;
    /**
     * loading提示
     */
    private TextView tv_loading;
    /**
     * 加载错误布局
     */
    private View layout_error;
    /**
     * 错误提示
     */
    private TextView tv_error;
    /**
     * 内容布局
     */
    private View contentView;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化控件
     *
     * @param context
     */
    public void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layout_loading = layoutInflater.inflate(R.layout.layout_loading, null);
        tv_loading = (TextView) layout_loading.findViewById(R.id.tv_loading);
        spinKitView = (SpinKitView) layout_loading.findViewById(R.id.spin_kit);
        addView(layout_loading);

        layout_error = layoutInflater.inflate(R.layout.layout_error, null);
        tv_error = (TextView) layout_error.findViewById(R.id.tv_error);
        addView(layout_error);
        //默认隐藏loading
        hideLoading();
    }

    /**
     * 显示loading
     */
    public void showLoading(CharSequence loadingStr) {
        if (layout_loading != null) {
            if (!TextUtils.isEmpty(loadingStr)) {
                tv_loading.setText(loadingStr);
            }
            layout_loading.setVisibility(View.VISIBLE);
            if (spinKitView != null) {
                spinKitView.getIndeterminateDrawable().start();
            }
        }
        hideContent();
        hideError();
    }

    /**
     * 隐藏loading
     */
    public void hideLoading() {
        if (layout_loading != null) {
            layout_loading.setVisibility(View.GONE);
            if (spinKitView != null) {
                spinKitView.getIndeterminateDrawable().stop();
            }
        }
    }

    /**
     * 显示错误提示
     */
    public void showError(CharSequence errorStr) {
        if (!TextUtils.isEmpty(errorStr)) {
            tv_error.setText(errorStr);
        }
        layout_error.setVisibility(View.VISIBLE);
        hideContent();
        hideLoading();
    }

    /**
     * 隐藏错误
     */
    public void hideError() {
        if(layout_error!=null)
            layout_error.setVisibility(View.GONE);
    }

    /**
     * 显示内容
     */
    public void showContent() {
        if (contentView != null)
            contentView.setVisibility(View.VISIBLE);
        hideError();
        hideLoading();
    }

    /**
     * 设置点击重新加载
     *
     * @param onClickListener
     */
    public void setErrorToReLoadListener(OnClickListener onClickListener) {
        if (tv_error != null && onClickListener != null)
            tv_error.setOnClickListener(onClickListener);
    }

    /**
     * 隐藏
     */
    public void hideContent() {
        if (contentView != null)
            contentView.setVisibility(View.GONE);
    }

    /**
     * @param view
     */
    public void setContentView(View view) {
        addView(view);
        contentView = view;
    }
}

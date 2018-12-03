package cn.colorfuline.base.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.colorfuline.base.R;
import cn.colorfuline.base.widget.CustomTitleBar;


/**
 * Created by CQDXP on 2016/7/7.
 */
public abstract class CFBaseFragment extends Fragment {
    protected RelativeLayout rootView;
    /**
     * 自定义标题栏
     */
    private CustomTitleBar customTitleBar;
    /**
     *
     */
    protected FrameLayout layout_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_base, container,
                false);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        findViews();
        setTitleBar(customTitleBar);
        loadContainerView(inflater);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isUseImmersive()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                customTitleBar.setClipToPadding(true);
                //fragment沉浸式状态栏问题，需手动设置customTitleBar padding，防止titlebar和通知栏重叠
                customTitleBar.setPadding(0, getStatusBarHeight(getContext().getApplicationContext()), 0, 0);
            }
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isUseImmersive() {
        return true;
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        //初始化内容填充布局
        layout_content = (FrameLayout) rootView.findViewById(R.id.layout_content);
        customTitleBar = (CustomTitleBar) rootView.findViewById(R.id.customTitleBar);
    }

    /**
     * 加载内容布局
     *
     * @param layoutInflater
     */
    private void loadContainerView(LayoutInflater layoutInflater) {
        if (getContainerViewId() > 0) {
            //添加到父布局中
            layout_content.addView(layoutInflater.inflate(getContainerViewId(), null));
        }
    }

    /**
     * 获取布局资源文件id
     *
     * @return
     */
    protected abstract int getContainerViewId();

    /**
     * 设置标题栏
     *
     * @param titleBar
     */
    protected abstract void setTitleBar(CustomTitleBar titleBar);

}

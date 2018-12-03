package cn.colorfuline.base.activity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.colorfuline.base.R;
import cn.colorfuline.base.activity.swipeBack.SwipeBackActivity;
import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.base.widget.CustomTitleBar;


/**
 * Created by CQDXP on 2016/7/3.
 */
public abstract class CFBaseActivity extends SwipeBackActivity {
    /**
     * 跟布局
     */
    protected RelativeLayout rootView;
    /**
     * 自定义标题栏
     */
    private CustomTitleBar customTitleBar;
    /**
     * 内容布局
     */
    private FrameLayout layout_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        findViews();
        setTitleBar(customTitleBar);
        if (isUseImmersive()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                customTitleBar.setFitsSystemWindows(true);
                customTitleBar.setClipToPadding(true);
                int statusBarHeight = getStatusBarHeight(getApplicationContext());
//                customTitleBar.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.default_title_height) + statusBarHeight;
                customTitleBar.setPadding(0, statusBarHeight, 0, 0);
            }
        }
        loadContainerView();
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
        rootView = (RelativeLayout) findViewById(R.id.layout_base_rootView);
        customTitleBar = (CustomTitleBar) findViewById(R.id.customTitleBar);
        layout_content = (FrameLayout) findViewById(R.id.layout_content);
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isUseImmersive() {
        return true;
    }

    /**
     * 加载内容布局
     */
    private void loadContainerView() {
        if (getContainerViewId() > 0) {
            layout_content.addView(getLayoutInflater().inflate(getContainerViewId(), null));
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

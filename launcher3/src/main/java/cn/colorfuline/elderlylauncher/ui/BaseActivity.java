package cn.colorfuline.elderlylauncher.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.colorfuline.base.activity.CFBaseActivity;
import cn.colorfuline.elderlylauncher.MApplication;

/**
 * Created by Administrator on 2017/3/2.
 */

public abstract class BaseActivity extends CFBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = MApplication.getInstance().getFontScale(); //1 设置正常字体大小的倍数
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

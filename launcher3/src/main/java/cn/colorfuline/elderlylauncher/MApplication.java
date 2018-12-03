package cn.colorfuline.elderlylauncher;

import android.app.Application;

import com.android.launcher3.DeviceProfile;

import cn.colorfuline.elderlylauncher.utils.SPUtil;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MApplication extends Application {

    /**
     * 字体大小
     */
    private float fontScale = 1.0f;

    public float getFontScale() {
        return fontScale;
    }

    public void setFontScale(float fontScale) {
        this.fontScale = fontScale;
    }

    private static MApplication mApplication;

    /**
     * 单例
     *
     * @return
     */
    public static MApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        fontScale = SPUtil.getFloat(this, DeviceProfile.SPKEY_FONTSCALE, DeviceProfile.DEFAULT_FONTSCALE);
    }
}

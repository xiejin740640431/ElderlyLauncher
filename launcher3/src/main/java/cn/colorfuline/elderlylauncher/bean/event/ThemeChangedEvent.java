package cn.colorfuline.elderlylauncher.bean.event;

/**
 * Created by Administrator on 2017/1/17.
 */

public class ThemeChangedEvent {
    private int styleId;

    public ThemeChangedEvent(int styleId) {
        this.styleId = styleId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }
}

package cn.colorfuline.elderlylauncher.events;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ChangeEnableSpeakEvent {
    private boolean enableSpeak;

    public ChangeEnableSpeakEvent(boolean enableSpeak) {
        this.enableSpeak = enableSpeak;
    }

    public boolean isEnableSpeak() {
        return enableSpeak;
    }

    public void setEnableSpeak(boolean enableSpeak) {
        this.enableSpeak = enableSpeak;
    }
}

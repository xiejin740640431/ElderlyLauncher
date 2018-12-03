package cn.colorfuline.elderlylauncher.events;

/**
 * Created by CQDXP on 2016/6/24.
 */
public class NetChangeEvent {
    private boolean networkAvailable;
    private String networkType;
    public NetChangeEvent(){}

    public NetChangeEvent(boolean networkAvailable, String networkType) {
        this.networkAvailable = networkAvailable;
        this.networkType = networkType;
    }

    public NetChangeEvent(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }
}

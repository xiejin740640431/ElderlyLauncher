package cn.colorfuline.elderlylauncher.https.requestBean;

/**
 * Created by CQDXP on 2016/6/23.
 */
public class CheckVersion {

    /**
     * appOs :
     * appRelease :
     * deviceType :
     */

    private String deviceType;
    private String appBuild;
    private String appIdentifier;

    public CheckVersion(String deviceType, String appBuild, String appIdentifier) {
        this.deviceType = deviceType;
        this.appBuild = appBuild;
        this.appIdentifier = appIdentifier;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppBuild() {
        return appBuild;
    }

    public void setAppBuild(String appBuild) {
        this.appBuild = appBuild;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }
}

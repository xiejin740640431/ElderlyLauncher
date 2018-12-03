/**
 * Copyright © 2015 zouhuo. All rights reserved.
 *
 * @Title: VersionBean.java
 * @Prject: BangBang
 * @Package: com.bangbang.bean
 * @Description: TODO
 * @author: XJ
 * @date: 2015-8-23 下午5:21:54
 * @version: V1.0
 */
package cn.colorfuline.elderlylauncher.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: VersionBean
 * @Description: 版本实体类
 * @author: XJ
 * @date: 2015-8-23 下午5:21:54  
 */
public class VersionBean implements Parcelable {
    /**
     * 强升
     */
    public static final int FORCED = 1;
    public static final String KEY = "VersionBean";
    public static final String LAST_NO_UPDATE_KEY = "lastNoUpdateTime";
    private Integer id;
    private String appName;
    private String appTitle;
    private String appSummary;
    private String appUrl;
    private Integer status;
    private String createTime;
    private String appOs;
    private String appRelease;
    private String appVersion;
    private Integer isForce;
    private Integer deviceType;
    private Integer appBuild;
    private String appIdentifier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppSummary() {
        return appSummary;
    }

    public void setAppSummary(String appSummary) {
        this.appSummary = appSummary;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAppOs() {
        return appOs;
    }

    public void setAppOs(String appOs) {
        this.appOs = appOs;
    }

    public String getAppRelease() {
        return appRelease;
    }

    public void setAppRelease(String appRelease) {
        this.appRelease = appRelease;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getIsForce() {
        return isForce;
    }

    public void setIsForce(Integer isForce) {
        this.isForce = isForce;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getAppBuild() {
        return appBuild;
    }

    public void setAppBuild(Integer appBuild) {
        this.appBuild = appBuild;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.appName);
        dest.writeString(this.appTitle);
        dest.writeString(this.appSummary);
        dest.writeString(this.appUrl);
        dest.writeValue(this.status);
        dest.writeString(this.createTime);
        dest.writeString(this.appOs);
        dest.writeString(this.appRelease);
        dest.writeString(this.appVersion);
        dest.writeValue(this.isForce);
        dest.writeValue(this.deviceType);
        dest.writeValue(this.appBuild);
        dest.writeString(this.appIdentifier);
    }

    public VersionBean() {
    }

    protected VersionBean(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appName = in.readString();
        this.appTitle = in.readString();
        this.appSummary = in.readString();
        this.appUrl = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createTime = in.readString();
        this.appOs = in.readString();
        this.appRelease = in.readString();
        this.appVersion = in.readString();
        this.isForce = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deviceType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appBuild = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appIdentifier = in.readString();
    }

    public static final Creator<VersionBean> CREATOR = new Creator<VersionBean>() {
        public VersionBean createFromParcel(Parcel source) {
            return new VersionBean(source);
        }

        public VersionBean[] newArray(int size) {
            return new VersionBean[size];
        }
    };
}

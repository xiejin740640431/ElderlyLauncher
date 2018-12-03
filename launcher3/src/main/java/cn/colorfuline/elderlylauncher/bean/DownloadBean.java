package cn.colorfuline.elderlylauncher.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DownloadBean implements Parcelable {
    private String packageName;
    private String title;
    private String filePath;
    private String downloadUrl;

    public DownloadBean(String title, String filePath, String downloadUrl) {
        this.title = title;
        this.filePath = filePath;
        this.downloadUrl = downloadUrl;
    }

    public DownloadBean(String packageName, String title, String filePath, String downloadUrl) {
        this.packageName = packageName;
        this.title = title;
        this.filePath = filePath;
        this.downloadUrl = downloadUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof DownloadBean){
            DownloadBean downloadBean = (DownloadBean) obj;
            if(downloadBean.getDownloadUrl().equals(downloadUrl)){
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.title);
        dest.writeString(this.filePath);
        dest.writeString(this.downloadUrl);
    }

    protected DownloadBean(Parcel in) {
        this.packageName = in.readString();
        this.title = in.readString();
        this.filePath = in.readString();
        this.downloadUrl = in.readString();
    }

    public static final Creator<DownloadBean> CREATOR = new Creator<DownloadBean>() {
        @Override
        public DownloadBean createFromParcel(Parcel source) {
            return new DownloadBean(source);
        }

        @Override
        public DownloadBean[] newArray(int size) {
            return new DownloadBean[size];
        }
    };
}

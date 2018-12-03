package cn.colorfuline.elderlylauncher.bean.request;

/**
 * Created by Administrator on 2017/1/4.
 */

public class RequestInformationBean {

    /**
     * userId : 1
     * channelId : 7
     * pageNumber : 1
     * pageSize : 5
     */

    private String userId;
    private String channelId;
    private int pageNumber;
    private int pageSize;

    public RequestInformationBean() {
    }

    public RequestInformationBean(String userId, String channelId, int pageNumber, int pageSize) {
        this.userId = userId;
        this.channelId = channelId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

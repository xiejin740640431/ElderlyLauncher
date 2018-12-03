package cn.colorfuline.elderlylauncher.bean.request;

/**
 * 获取服务类型请求实体类
 * Created by MarcoZhan on 2016/12/27.
 */

public class RequestServiceTypeBean {

    String userId;
    String parentId;

    public RequestServiceTypeBean(String userId, String parentId) {
        this.userId = userId;
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


}

package cn.colorfuline.elderlylauncher.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 服务类型返回实体类
 * Created by MarcoZhan on 2016/12/2.
 */

public class ServiceTypeBean {


    /**
     * id : 2
     * typeName : 配餐
     * typeCode : CT
     * parentId : 0
     * status : 1
     * createTime : 2016-11-30 19:19:30
     * createId : 0
     * updateTime : 2016-11-30 19:19:35
     * updateId : 0
     */

    private String id;
    private String typeName;
    private String typeCode;
    private String parentId;
    private String status;
    private String createTime;
    private String createId;
    private String updateTime;
    private String updateId;

    public static ServiceTypeBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), ServiceTypeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}

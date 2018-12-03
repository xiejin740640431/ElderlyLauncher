package cn.colorfuline.elderlylauncher.bean;

/**
 * Created by MarcoZhan on 2016/11/14.
 */

public class ServiceBean {

    private String name;
    private String imgUrl;
    private int imgId;
    private String typeId;

    public ServiceBean() {
    }

    public ServiceBean(String name, int imgId, String imgUrl, String typeId) {
        this.name = name;
        this.imgId = imgId;
        this.imgUrl = imgUrl;
        this.typeId = typeId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}

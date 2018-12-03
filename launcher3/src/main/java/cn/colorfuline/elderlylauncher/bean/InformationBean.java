package cn.colorfuline.elderlylauncher.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class InformationBean {
    private int sectionFirstPosition;
    private boolean isHeader;
    private String lastTime;

    private String qiniuImgLink;
    private String img;
    private String id;
    private String title;
    private String content;
    private String keyword;
    private String referenceUrl;
    private String createTime;
    private String weekDay;

    List<InformationDiggListBean> headTopBanner;

    public List<InformationDiggListBean> getHeadTopBanner() {
        return headTopBanner;
    }

    public void setHeadTopBanner(List<InformationDiggListBean> headTopBanner) {
        this.headTopBanner = headTopBanner;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQiniuImgLink() {
        return qiniuImgLink;
    }

    public void setQiniuImgLink(String qiniuImgLink) {
        this.qiniuImgLink = qiniuImgLink;
    }

    public InformationBean(int sectionFirstPosition, boolean isHeader, String lastTime, String weekDay) {
        this.sectionFirstPosition = sectionFirstPosition;
        this.isHeader = isHeader;
        this.lastTime = lastTime;
        this.weekDay = weekDay;
    }

    public InformationBean() {
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getSectionFirstPosition() {
        return sectionFirstPosition;
    }

    public void setSectionFirstPosition(int sectionFirstPosition) {
        this.sectionFirstPosition = sectionFirstPosition;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}

package cn.colorfuline.elderlylauncher.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class InformationParseBean {
    private int pageNumber;
    private int pageSize;
    private int totalCount;
    private int pageCount;
    private List<InformationBean> list;
    private List<InformationDiggListBean> diggList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<InformationBean> getList() {
        return list;
    }

    public void setList(List<InformationBean> list) {
        this.list = list;
    }

    public List<InformationDiggListBean> getDiggList() {
        return diggList;
    }

    public void setDiggList(List<InformationDiggListBean> diggList) {
        this.diggList = diggList;
    }
}

package cn.colorfuline.elderlylauncher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Contacts implements Serializable {
    private String userName;
    private String phoneNum;
    private byte[] iconByte;

    public Contacts() {
    }

    public Contacts(String userName, String phoneNum, byte[] iconByte) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.iconByte = iconByte;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public byte[] getIconByte() {
        return iconByte;
    }

    public void setIconByte(byte[] iconByte) {
        this.iconByte = iconByte;
    }
}

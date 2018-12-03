package cn.colorfuline.elderlylauncher.bean;

/**
 * Created by Administrator on 2017/3/16.
 */

public class UserBean {
    String userName;
    String userPw;
    String nickName;
    String userAvatar;
    String userSex;
    String userBirthday;
    String userAddr;
    String userId;

    public UserBean(String userName, String userPw, String nickName, String userAvatar, String userSex, String userBirthday, String userAddr, String userId) {
        this.userName = userName;
        this.userPw = userPw;
        this.nickName = nickName;
        this.userAvatar = userAvatar;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userAddr = userAddr;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }
}

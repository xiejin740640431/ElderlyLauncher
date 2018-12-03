package cn.colorfuline.elderlylauncher.bean.request;

/**
 * Created by CQDXP on 2016/5/28.
 */
public class GetEmConCard {
    private String userId;

    public GetEmConCard(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

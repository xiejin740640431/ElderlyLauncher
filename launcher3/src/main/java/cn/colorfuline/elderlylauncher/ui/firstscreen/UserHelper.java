package cn.colorfuline.elderlylauncher.ui.firstscreen;

import android.content.Context;

import cn.colorfuline.elderlylauncher.bean.UserBean;


/**
 * Created by Administrator on 2017/3/16.
 */

public class UserHelper {
    private static UserHelper instance;
    private Context appContext;
    private UserBean userBean;

    public synchronized static UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelper();
        }
        return instance;
    }
    public void init(Context appContext) {
        this.appContext = appContext;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean=userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

}

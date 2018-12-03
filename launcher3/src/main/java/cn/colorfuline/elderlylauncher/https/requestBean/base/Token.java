package cn.colorfuline.elderlylauncher.https.requestBean.base;

/**
 * Created by CQDXP on 2016/5/28.
 */
public class Token<T> {
    private String token;
    private T data;

    public Token(String token, T data) {
        this.token = token;
        this.data = data;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

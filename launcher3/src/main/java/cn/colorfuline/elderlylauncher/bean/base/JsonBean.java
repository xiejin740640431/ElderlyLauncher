package cn.colorfuline.elderlylauncher.bean.base;

import java.io.Serializable;

/**
 * jsonBean父类
 *
 * @author Administrator
 */
public class JsonBean<T> implements Serializable {

    /**
     * 序列化Id
     */
    private static final long serialVersionUID = 1L;
    private String token;
    private String status;
    private String code;
    private String message;
    private T response;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}

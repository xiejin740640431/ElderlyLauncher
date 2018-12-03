package cn.colorfuline.elderlylauncher.https.intercept;

import com.google.gson.JsonObject;

import java.io.IOException;

import cn.colorfuline.elderlylauncher.https.RetrofitUtil;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by CQDXP on 2016/5/7.
 */
public class NormalIntercept implements Interceptor {
    private final String TAG = NormalIntercept.class.getName();


    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //加密后的request
        Request newRequest = null;
        //判断请求方式
        if ("GET".equals(request.method())) {
            newRequest = request;
        } else if ("POST".equals(request.method())) {
            RequestBody requestBody = request.body();
            //请求体value加密
            if (requestBody instanceof FormBody) {
                //创建新包体
                RequestBody newRequestBody = null;
                FormBody oidFormBody = (FormBody) request.body();
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    jsonObject.addProperty(oidFormBody.name(i), oidFormBody.value(i));
                }
                newRequestBody = RequestBody.create(RetrofitUtil.JSON_CONTENT_TYPE, jsonObject.toString());
                //创建新requeust
                if (newRequestBody != null)
                    newRequest = request.newBuilder().method(request.method(), newRequestBody).build();
            }
        }
        //发送请求
        return chain.proceed(newRequest == null ? request : newRequest);
    }
}

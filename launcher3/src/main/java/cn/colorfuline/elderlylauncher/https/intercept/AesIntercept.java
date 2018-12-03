package cn.colorfuline.elderlylauncher.https.intercept;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.Charset;

import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.elderlylauncher.config.Config;
import cn.colorfuline.elderlylauncher.https.RetrofitUtil;
import cn.colorfuline.elderlylauncher.utils.Encryptor;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by CQDXP on 2016/5/7.
 */
public class AesIntercept implements Interceptor {
    private final String TAG = AesIntercept.class.getName();


    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //加密后的request
        Request newRequest = null;
        //判断请求方式
        if ("GET".equals(request.method())) {
        } else if ("POST".equals(request.method())) {
            RequestBody requestBody = request.body();
            //请求体value加密
            if (requestBody instanceof FormBody) {
                FormBody oidFormBody = (FormBody) requestBody;
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    jsonObject.addProperty(oidFormBody.name(i), oidFormBody.value(i));
                }
                LogUtil.i(TAG, "request body ：" + jsonObject != null ? jsonObject.toString() : "");
                RequestBody newRequestBody = RequestBody.create(RetrofitUtil.CONTENT_TYPE, Encryptor.encrypt(jsonObject.toString(), Config.AES_KEY));
                newRequest = request.newBuilder().method(request.method(), newRequestBody).build();
            } else {
                if (requestBody.contentLength() > 0) {
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String str = buffer.readString(Charset.forName(RetrofitUtil.UTF8));
                    LogUtil.i(TAG, "request body ：" + str);
                    String encryptorStr = Encryptor.encrypt(str, Config.AES_KEY);
                    RequestBody encryprotRequestBody = RequestBody.create(RetrofitUtil.CONTENT_TYPE, encryptorStr);
                    newRequest = request.newBuilder().method(request.method(), encryprotRequestBody).build();
                }
            }
        }
        //发送请求
        Response response = chain.proceed(newRequest == null ? request : newRequest);
        //获取responsebody
        ResponseBody responseBody = response.body();
        String responseStr = responseBody.string();
        //解密包体
        try {
            responseStr = Encryptor.decrypt(responseStr, Config.AES_KEY);
            LogUtil.i(TAG, "Encryptor decrypt result:" + responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseBody newResponseBody = ResponseBody.create(responseBody.contentType(), responseStr);
        //创建新的response并返回
        return response.newBuilder().body(newResponseBody).build();
    }
}

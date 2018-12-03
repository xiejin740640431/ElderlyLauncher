package cn.colorfuline.elderlylauncher.https;

import cn.colorfuline.elderlylauncher.https.api.FamilyApi;
import cn.colorfuline.elderlylauncher.https.intercept.AesIntercept;
import cn.colorfuline.elderlylauncher.https.intercept.NormalIntercept;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/4/12.
 */
public class RetrofitFamilyUtil {
    /**
     * 服务器地址
     */
    public static final String API_URL = "http://api.colorfuline.cn/441410BE34051EE0/";
//    public static final String API_URL ="http://csapi.colorfuline.cn/colorfulineSP41/";//线下

    /**
     * text/plain utf-8 contentType
     */
    public static final MediaType CONTENT_TYPE = MediaType.parse("text/plain; charset=utf-8");
    /**
     * application/json utf-8 contentType
     */
    public static final MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    /**
     * 单例对象
     */
    private static RetrofitFamilyUtil retrofitUtil = new RetrofitFamilyUtil();
    /**
     * Retrofit对象
     */
    private Retrofit retrofit;
    private FamilyApi familyApi;

    /**
     *
     */
    private RetrofitFamilyUtil() {
        retrofit = createRetrofit(true);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RetrofitFamilyUtil getInstance() {
        return retrofitUtil;
    }

    /**
     * 创建retrofit对象
     *
     * @param isUesAes
     * @return
     */
    public static Retrofit createRetrofit(boolean isUesAes) {
        //创建okhttp实例
        OkHttpClient.Builder build = new OkHttpClient.Builder();
        //添加aes加密、解密拦截器
        build.addInterceptor(isUesAes ? new AesIntercept() : new NormalIntercept());
        //添加日志
        if (true) {
            //创建日志Interceptor
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            build.addInterceptor(httpLoggingInterceptor);
        }
        //创建Build
        Retrofit.Builder retrofitBuild = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(build.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        //判断是否使用加密GsonFactory
        retrofitBuild.addConverterFactory(GsonConverterFactory.create());
        return retrofitBuild.build();
    }

    /**
     * 创建api实例
     *
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> T create(Class<T> tClass) {
        return retrofit.create(tClass);
    }


    public FamilyApi getFamilyApiInstance() {
        if (familyApi == null) {
            familyApi = retrofit.create(FamilyApi.class);
        }
        return familyApi;
    }


}

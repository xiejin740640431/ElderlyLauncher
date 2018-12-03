package cn.colorfuline.elderlylauncher.https;



import cn.colorfuline.elderlylauncher.https.api.CommonApi;
import cn.colorfuline.elderlylauncher.https.intercept.AesIntercept;
import cn.colorfuline.elderlylauncher.https.intercept.NormalIntercept;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CQDXP on 2016/7/3.
 */
public class RetrofitUtil {
    public static final String API_URL = "http://10.80.84.26:8080/colorfulineSP41/";
    public static final String UTF8 = "utf-8";
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
    private static RetrofitUtil retrofitUtil = new RetrofitUtil();
    /**
     * Retrofit对象
     */
    private Retrofit retrofit;

    /**
     *
     */
    private RetrofitUtil() {
        retrofit = createRetrofit(true);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        return retrofitUtil;
    }

    /**
     * @return
     */
    public Retrofit getRetrofit() {
        return retrofit;
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
        if (isUesAes) {
            retrofitBuild.addConverterFactory(GsonConverterFactory.create());
        } else {
            retrofitBuild.addConverterFactory(GsonConverterFactory.create());
        }
        return retrofitBuild.build();
    }

    /**
     * 通用api实例
     */
    private CommonApi commonApi;

    /**
     * 获取通用api
     * @return
     */
    public CommonApi getCommonApi() {
        if (commonApi == null) {
            commonApi = retrofit.create(CommonApi.class);
        }
        return commonApi;
    }
}

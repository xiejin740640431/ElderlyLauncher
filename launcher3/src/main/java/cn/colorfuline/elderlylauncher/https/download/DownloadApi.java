package cn.colorfuline.elderlylauncher.https.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by CQDXP on 2016/8/16.
 */
public interface DownloadApi {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}

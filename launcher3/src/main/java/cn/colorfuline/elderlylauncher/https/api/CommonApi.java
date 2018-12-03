package cn.colorfuline.elderlylauncher.https.api;

import cn.colorfuline.elderlylauncher.bean.VersionBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.https.requestBean.CheckVersion;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface CommonApi {
    /**
     * 检查新版本
     *
     * @param checkVersionToken
     * @return
     */
    @POST("user/app/checkNewestAPP.do")
    Observable<JsonBean<VersionBean>> checkVersion(@Body Token<CheckVersion> checkVersionToken);
}

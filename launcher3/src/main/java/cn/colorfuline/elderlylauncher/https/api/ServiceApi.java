package cn.colorfuline.elderlylauncher.https.api;

import java.util.List;

import cn.colorfuline.elderlylauncher.bean.InformationParseBean;
import cn.colorfuline.elderlylauncher.bean.ServiceTypeBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.bean.request.RequestInformationBean;
import cn.colorfuline.elderlylauncher.bean.request.RequestServiceTypeBean;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface ServiceApi {
    /**
     * 获取服务类型
     *
     * @return
     */
    @POST("app/service/lookServiceType")
    Call<JsonBean<List<ServiceTypeBean>>> getServiceType(@Body Token<RequestServiceTypeBean> token);

    /**
     * 获取资讯列表
     * @return
     */
    @POST("app/cms/contentPageIndex")
    Observable<JsonBean<InformationParseBean>> getInformations(@Body Token<RequestInformationBean> token);

}

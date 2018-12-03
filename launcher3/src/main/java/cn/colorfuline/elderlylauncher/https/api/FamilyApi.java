package cn.colorfuline.elderlylauncher.https.api;

import cn.colorfuline.elderlylauncher.bean.EmergencyContactCardInfo;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.bean.request.GetEmConCard;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface FamilyApi {
    /**
     * 获取紧急联系人
     * @param token
     * @return
     */
    @POST("user/app/getEmergencyContactCard.do")
    Call<JsonBean<EmergencyContactCardInfo>> getEmConCardInfo(@Body Token<GetEmConCard> token);

}

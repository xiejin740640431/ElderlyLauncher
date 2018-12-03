package cn.colorfuline.elderlylauncher.ui.firstscreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.List;

import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.elderlylauncher.bean.EmergencyContactCardInfo;


/**
 * Created by Administrator on 2016/4/26.
 */
public class PhoneUtil {
    /**
     * 获取手机号(部分手机上有用)
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    /**
     * 打电话
     */
    public static void callPhone(List<EmergencyContactCardInfo.ContactsBean> list, Context context) {
        if (list == null || list.isEmpty()) {
            CustomToast.showShort(context, "没有找到紧急联系人");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsDefaultBoolean()) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + list.get(i).getContactNo()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // 不需要解释为何需要该权限，直接请求授权
//                    ActivityCompat.requestPermissions(MsgZhtxAct.this,new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
//                }
                return;
                }
                context.startActivity(intent);
                break;
            }
        }
    }
}

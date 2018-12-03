package cn.colorfuline.base.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import cn.colorfuline.base.R;
import cn.colorfuline.base.utils.StringUtils;


/**
 * Created by CQDXP on 2016/6/4.
 */
public class CustomToast extends Toast {
    private RelativeLayout layout_custom_toast;
    private TextView tv_toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CustomToast(Context context) {
        super(context);
        layout_custom_toast = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null);
        tv_toast = (TextView) layout_custom_toast.findViewById(R.id.tv_toast);
        setView(layout_custom_toast);
//        setParams();
    }

    /**
     * 设置参数
     */
    public void setParams(){
        try {
            Object mTN = null;
            mTN = getField(this, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = R.style.anim_toast;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    /**
     * @param s
     */
    public void setTextStr(CharSequence s) {
        if (!StringUtils.isEmpty(s)) {
            tv_toast.setText(s);
        }
    }

    /**
     *
     */
    public void setTopNormalGravity() {
        setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
    }

    /**
     * @param color
     */
    public void setBackground(int color) {
        layout_custom_toast.setBackgroundColor(color);
    }

    /**
     * @param resourceId
     */
    public static void showShort(Context context, int resourceId) {
        showShort(context, context.getString(resourceId));
    }

    /**
     * @param msg
     */
    public static void showShort(Context context, CharSequence msg) {
        CustomToast customToast = new CustomToast(context);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setTextStr(msg);
        customToast.show();
    }

    /**
     * @param resourceId
     */
    public static void showLong(Context context, int resourceId) {
        showLong(context, context.getString(resourceId));
    }

    /**
     * @param msg
     */
    public static void showLong(Context context, CharSequence msg) {
        CustomToast customToast = new CustomToast(context);
        customToast.setDuration(Toast.LENGTH_LONG);
        customToast.setTextStr(msg);
        customToast.show();
    }
}

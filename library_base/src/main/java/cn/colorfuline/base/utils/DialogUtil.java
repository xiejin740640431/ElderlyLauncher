package cn.colorfuline.base.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cn.colorfuline.base.R;


/**
 * Created by CQDXP on 2016/5/26.
 */
public class DialogUtil {
    private static ProgressDialog progressDialog = null;

    /**
     * 显示dialog
     *
     * @param context
     * @param msg
     */
    public static void showDialog(Context context, String msg) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog = null;
                    }
                });
            }
            progressDialog.setMessage(msg);
            progressDialog.show();
            setDialogFontSize(progressDialog, context.getResources().getDimensionPixelSize(R.dimen.sp_16));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dialog
     * @param size
     */
    private static void setDialogFontSize(Dialog dialog, int size) {
        try{
            Window window = dialog.getWindow();
            View view = window.getDecorView();
            setViewFontSize(view, size);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置字体大小
     *
     * @param view
     * @param size
     */
    private static void setViewFontSize(View view, int size) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                setViewFontSize(parent.getChildAt(i), size);
            }
        } else if (view instanceof TextView) {
            TextView textview = (TextView) view;
            textview.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }
    }

    /**
     * 隐藏dialog
     */
    public static void dismissDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

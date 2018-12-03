package cn.colorfuline.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.colorfuline.base.R;
import cn.colorfuline.base.spinkit.SpinKitView;


/**
 * Created by CQDXP on 2016/7/10.
 */
public class LoadingDialog extends Dialog {
    /**
     *
     */
    SpinKitView spinKit;
    /**
     *
     */
    TextView tvLoading;
    /**
     * 加载文字
     */
    private CharSequence loadingStr = "数据加载中...";

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_loading, null);
        setContentView(view);
        spinKit = (SpinKitView) findViewById(R.id.spin_kit);
        tvLoading = (TextView) findViewById(R.id.tv_loading);
        tvLoading.setText(loadingStr);
    }

    @Override
    public void show() {
        super.show();
        spinKit.getIndeterminateDrawable().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        spinKit.getIndeterminateDrawable().stop();
    }

    /**
     * @param loadingStr
     */
    public LoadingDialog setLoadingStr(CharSequence loadingStr) {
        this.loadingStr = loadingStr;
        if (tvLoading != null) {
            tvLoading.setText(loadingStr);
        }
        return this;
    }
}

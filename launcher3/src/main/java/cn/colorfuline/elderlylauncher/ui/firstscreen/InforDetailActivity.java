package cn.colorfuline.elderlylauncher.ui.firstscreen;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.colorfuline.base.utils.NetWorkUtil;
import cn.colorfuline.base.utils.StringUtils;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.config.Config;

public class InforDetailActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    /**
     * 浏览器设置对象
     */
    private WebSettings webseting;

    /**
     * 网页地址
     */
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题拦
        setContentView(R.layout.activity_infor_detail);
        ButterKnife.bind(this);
        initWebView();
        getIntentData();
    }

    public void initWebView() {
        webseting = mWebView.getSettings();
        webseting.setSupportZoom(false);
        webseting.setBuiltInZoomControls(false);// 隐藏缩放按钮
        webseting.setUseWideViewPort(true);// 可任意比例缩放
        webseting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        if (!NetWorkUtil.isNetworkConnected(InforDetailActivity.this)) {
            webseting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!StringUtils.isEmpty(url)) {
                    mWebView.loadUrl(url);
                }
                return true;
            }
        });
    }



    /**
     * @Title: getIntentData
     * @Description: 获取传递来的数据
     * @return: void
     */
    public void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            url = bundle.getString("url");
            String contentId = bundle.getString("contentId");//资讯id
            if (!TextUtils.isEmpty(url))
                mWebView.loadUrl(url);
            else
                mWebView.loadUrl(Config.HELP_ABOUT);
        } else {
            mWebView.loadUrl(Config.HELP_ABOUT);
        }
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * @ClassName: WebChromeClient
     * @Description: TODO
     * @author: XJ
     * @date: 2015-9-20 下午6:24:40
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                pbLoading.setVisibility(View.GONE);
            } else {
                if (pbLoading.getVisibility() == View.GONE)
                    pbLoading.setVisibility(View.VISIBLE);
                pbLoading.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }
}

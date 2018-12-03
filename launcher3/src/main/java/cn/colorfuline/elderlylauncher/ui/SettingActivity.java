package cn.colorfuline.elderlylauncher.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.LauncherProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.elderlylauncher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.colorfuline.base.dialog.LoadingDialog;
import cn.colorfuline.base.rxbus.RxBus;
import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.base.widget.CustomTitleBar;
import cn.colorfuline.elderlylauncher.MApplication;
import cn.colorfuline.elderlylauncher.bean.DownloadBean;
import cn.colorfuline.elderlylauncher.bean.VersionBean;
import cn.colorfuline.elderlylauncher.bean.base.JsonBean;
import cn.colorfuline.elderlylauncher.events.ChangeEnableSpeakEvent;
import cn.colorfuline.elderlylauncher.https.RetrofitUtil;
import cn.colorfuline.elderlylauncher.https.requestBean.CheckVersion;
import cn.colorfuline.elderlylauncher.https.requestBean.base.Token;
import cn.colorfuline.elderlylauncher.services.DownloadService;
import cn.colorfuline.elderlylauncher.services.ReadScreenService;
import cn.colorfuline.elderlylauncher.utils.AppUtils;
import cn.colorfuline.elderlylauncher.utils.DateFormatUtil;
import cn.colorfuline.elderlylauncher.utils.SPUtil;
import cn.colorfuline.elderlylauncher.utils.SilentInstaller;
import cn.colorfuline.elderlylauncher.utils.StorageUtil;
import cn.colorfuline.elderlylauncher.utils.comparators.VersionStringComparator;
import cn.colorfuline.elderlylauncher.widget.RangeSeekbar;
import cn.colorfuline.elderlylauncher.widget.ReadTextView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SettingActivity extends BaseActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.tv_current_version)
    TextView tvCurrentVersion;
    @BindView(R.id.switch_read_screen)
    Switch switchReadScreen;
    @BindView(R.id.seekbar)
    RangeSeekbar seekbar;
    CustomTitleBar titleBar;
    private boolean isFontScaleChange = false;

    @Override
    protected int getContainerViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setTitleBar(CustomTitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setTitle(R.string.str_setting);
        titleBar.setBackVisible(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViewInfo();
    }

    /**
     * 初始化控件
     */
    public void initViewInfo() {
        tvCurrentVersion.setText("V" + AppUtils.getVersionName(getApplicationContext()));
        if (SPUtil.getBoolean(getApplicationContext(), ReadScreenService.SP_ENABLE_SPEAK_KEY)) {
            switchReadScreen.setChecked(true);
        }
        seekbar.setValue(MApplication.getInstance().getFontScale());
        seekbar.setOnRangeChangedListener(new RangeSeekbar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekbar view, float currentValue, float max, boolean isFromUser) {
                LogUtil.i("SettingFragment", "--------->" + currentValue + "---------->" + max + "======>" + isFromUser);
                isFontScaleChange = true;
                MApplication.getInstance().setFontScale(currentValue);
                SPUtil.putFloat(getApplicationContext(), DeviceProfile.SPKEY_FONTSCALE, currentValue);
                refreshTextSize();
            }

            @Override
            public void onRangeChanging(RangeSeekbar view, float min, float max, boolean isFromUser) {

            }
        });
    }

    /**
     * @param buttonView
     * @param isChecked
     */
    @OnCheckedChanged(R.id.switch_read_screen)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        RxBus.getDefault().post(new ChangeEnableSpeakEvent(isChecked));
    }

    /**
     * 点击监听
     *
     * @param v
     */
    @OnClick({R.id.layout_skin, R.id.layout_system_setting, R.id.layout_read_screen, R.id.layout_reset_launcher, R.id.layout_system_launcher, R.id.layout_current_version})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_skin:
                break;
            case R.id.layout_system_setting:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
            case R.id.layout_read_screen:
                switchReadScreen.setChecked(!switchReadScreen.isChecked());
                break;
            case R.id.layout_reset_launcher:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.str_confirm_reset_launcher))
                        .setNegativeButton(getString(R.string.str_cancle), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(getString(R.string.str_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetLauncher();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.layout_system_launcher:
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + getPackageName()));
                startActivity(i);
                break;
            case R.id.layout_current_version:
                checkNewVersion();
                break;
        }
    }

    /**
     * 检测新版本
     */
    public void checkNewVersion() {
        final LoadingDialog loadingDialog = new LoadingDialog(this)
                .setLoadingStr(getString(R.string.str_check_updating));
        loadingDialog.show();
        RetrofitUtil
                .getInstance()
                .getCommonApi()
                .checkVersion(new Token<>("", new CheckVersion("1", AppUtils.getVersionName(getApplicationContext())
                        , getPackageName())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JsonBean<VersionBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadingDialog.dismiss();
                        CustomToast.showShort(getApplicationContext(), "检测新版本失败，请稍后再试");
                    }

                    @Override
                    public void onNext(JsonBean<VersionBean> response) {
                        loadingDialog.dismiss();
                        if (response != null) {
                            if ("1".equals(response.getStatus())) {
                                checkVersionStr(response.getResponse(), getApplicationContext(), false);
                            } else {
                                CustomToast.showShort(getApplicationContext(), "已是最新版本！");
                                LogUtil.i(TAG, "check version failed,message," + response.getMessage());
                            }
                        } else {
                            CustomToast.showShort(getApplicationContext(), "已是最新版本！");
                            LogUtil.i(TAG, "check version failed");
                        }
                    }
                });
    }

    /**
     * 判断版本号
     */
    public void checkVersionStr(final VersionBean versionBean, Context context, boolean isCheckLastNoUpdateTime) {
        if (versionBean == null) {
            return;
        }
        try {
            if (isCheckLastNoUpdateTime && DateFormatUtil.formatDate(new Date(), DateFormatUtil.DEFAULT_DATE_PATTERN).equals(SPUtil.getString(context.getApplicationContext(), VersionBean.LAST_NO_UPDATE_KEY))) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (new VersionStringComparator().compare(versionBean.getAppVersion(), AppUtils.getVersionName(context)) > 0) {
            new AlertDialog.Builder(this)
                    .setMessage("检测到新本版" + versionBean.getAppVersion() + ",是否立即升级？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String title = "launcher";
                            String filePath = StorageUtil.FileCachePath.getApkDownLoadPath(getApplicationContext(), title);
                            if (new File(filePath).exists()) {
                                new SilentInstaller().install(getApplicationContext(), new File(filePath));
                            } else {
                                ArrayList<DownloadBean> list = new ArrayList<>();
                                list.add(new DownloadBean("cn.colorfuline.elderlylauncher",title, StorageUtil.FileCachePath.getApkDownLoadPath(getApplicationContext(), title), versionBean.getAppUrl()));
                                Intent downloadIntent = new Intent(SettingActivity.this, DownloadService.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(DownloadService.EXTRA_ACTION, DownloadService.ACTION_DOWNLOAD);
                                bundle.putParcelableArrayList(DownloadService.EXTRA_DOWNLOAD_LIST, list);
                                downloadIntent.putExtras(bundle);
                                startService(downloadIntent);
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
        }
    }

    /**
     * 重置桌面
     */
    public void resetLauncher() {
        Intent intent = new Intent(Launcher.ACTION_RESET_LAUNCHER);
        sendBroadcast(intent);
        finish();
    }

    @BindView(R.id.tv_str_font)
    ReadTextView tvStrFont;
    @BindView(R.id.tv_str_system_setting)
    ReadTextView tvStrSystemSetting;
    @BindView(R.id.tv_str_skin)
    ReadTextView tvStrSkin;
    @BindView(R.id.tv_str_read_screen)
    ReadTextView tvStrReadScreen;
    @BindView(R.id.tv_str_reset_launcher)
    ReadTextView tvStrResetLauncher;
    @BindView(R.id.tv_str_system_launcher)
    ReadTextView tvStrSystemLauncher;
    @BindView(R.id.tv_str_current_version)
    ReadTextView tvStrCurrentVersion;

    public void refreshTextSize() {
        float textSize = getResources().getDimension(R.dimen.default_setting_text_size);
        tvStrFont.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrSystemSetting.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrSkin.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrReadScreen.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrResetLauncher.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrSystemLauncher.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvStrCurrentVersion.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvCurrentVersion.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        titleBar.refreshTitleSize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFontScaleChange) {
            LauncherAppState.getInstance().getInvariantDeviceProfile().portraitProfile.setFontScale(MApplication.getInstance().getFontScale());
            LauncherAppState.getInstance().getInvariantDeviceProfile().landscapeProfile.setFontScale(MApplication.getInstance().getFontScale());
            LauncherAppState.getInstance().getModel().forceReload();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                finish();
            }
        }
    }
}

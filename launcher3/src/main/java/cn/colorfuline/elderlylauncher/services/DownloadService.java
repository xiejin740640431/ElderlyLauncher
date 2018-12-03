package cn.colorfuline.elderlylauncher.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.colorfuline.elderlylauncher.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cn.colorfuline.base.rxbus.RxBus;
import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.base.utils.NetWorkUtil;
import cn.colorfuline.base.utils.StringUtils;
import cn.colorfuline.base.view.CustomToast;
import cn.colorfuline.elderlylauncher.bean.DownloadBean;
import cn.colorfuline.elderlylauncher.events.NetChangeEvent;
import cn.colorfuline.elderlylauncher.https.download.DownloadManager;
import cn.colorfuline.elderlylauncher.https.download.DownloadProgressListener;
import cn.colorfuline.elderlylauncher.utils.NotifyUtil;
import cn.colorfuline.elderlylauncher.utils.SPUtil;
import cn.colorfuline.elderlylauncher.utils.SilentInstaller;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DownloadService extends Service {
    public static final String TAG = DownloadService.class.getSimpleName();
    public static final String EXTRA_DOWNLOAD_LIST = "downloadList";
    public static final String EXTRA_ACTION = "action";
    public static final String ACTION_CHECK_FAVORITES_AND_DOWNLOAD = "checkFavoritesAndDownload";
    public static final String ACTION_DOWNLOAD = "download";
    /**
     * 消息队列
     */
    private Queue<DownloadBean> downloadQueue = new LinkedList<>();
    /**
     *
     */
    private DownloadBean currentDownloadBean;
    /**
     * 是否允许下载
     */
    private boolean enableDownload = true;
    /**
     * 是否正在下载
     */
    private boolean isDownloading = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkNetEnableDownload();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            String action = bundle.getString(EXTRA_ACTION);
            if (ACTION_DOWNLOAD.equals(action)) {
                List<DownloadBean> list = bundle.getParcelableArrayList(EXTRA_DOWNLOAD_LIST);
                addAndDownload(list);
                checkAndDownload();
            } else if (ACTION_CHECK_FAVORITES_AND_DOWNLOAD.equals(action)) {
            }
        } else {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 添加并下载
     *
     * @param list
     */
    public void addAndDownload(List<DownloadBean> list) {
        LogUtil.i(TAG, "------->" + downloadQueue + "-------->" + list);
        if (currentDownloadBean != null) {
            int index = list.indexOf(currentDownloadBean);
            if (index >= 0) {
                list.remove(index);
            }
        }
        for (DownloadBean downloadBean : list) {
            if (downloadQueue.contains(downloadBean)) {
                list.remove(downloadBean);
            }
        }
        downloadQueue.addAll(list);
    }

    /**
     * 判断下载队列
     */
    public synchronized void checkAndDownload() {
        if (downloadQueue != null && !downloadQueue.isEmpty()) {
            startDownload(currentDownloadBean = downloadQueue.poll());
        } else {
            stopSelf();
        }
    }

    /**
     * 开始下载
     */
    public void startDownload(final DownloadBean downloadBean) {
        if (!enableDownload) {
            return;
        }
        if (new File(downloadBean.getFilePath()).exists()) {
            install(downloadBean.getPackageName(), downloadBean.getTitle(), downloadBean.getFilePath());
            checkAndDownload();
            return;
        }
        final NotifyUtil notifyUtil = new NotifyUtil(this, (int) System.currentTimeMillis());
        //创建点击intent
        Intent intentNotify = new Intent();
        intentNotify.setAction(Intent.ACTION_VIEW);
        intentNotify.setDataAndType(Uri.fromFile(new File(downloadBean.getFilePath())), "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotify, PendingIntent.FLAG_UPDATE_CURRENT);
        //显示图标
        int smallIcon = R.mipmap.ic_launcher_home;
        String ticker = getResources().getString(R.string.str_downloading) + downloadBean.getTitle();
        //设置进度
        notifyUtil.notify_progress(pendingIntent, smallIcon, ticker, downloadBean.getTitle(), getResources().getString(R.string.str_downloading), false, false, false);
        notifyUtil.sent();
        DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
            private int lastpercent;

            @Override
            public void update(final long bytesRead, final long contentLength, boolean done) {
                double totalLength = contentLength;
                double readLength = bytesRead;
                double percent = ((readLength / totalLength) * 100);
                if (((int) percent) != lastpercent) {
                    notifyUtil.cBuilder.setProgress(100, (int) percent, false);
                    notifyUtil.sent();
                    lastpercent = (int) percent;
                    LogUtil.i(TAG, "download progress：已下载:" + percent + "%");
                }
            }
        };
        new DownloadManager(StringUtils.getHostName(downloadBean.getDownloadUrl()), downloadProgressListener)
                .downloadAPK(downloadBean.getDownloadUrl(), new File(downloadBean.getFilePath())
                        , new Subscriber() {
                            @Override
                            public void onCompleted() {
                                isDownloading = false;
                                notifyUtil.cBuilder.setContentText("下载完成").setProgress(0, 0, false);
                                notifyUtil.sent();
                                install(downloadBean.getPackageName(), downloadBean.getTitle(), downloadBean.getFilePath());
                                checkAndDownload();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                CustomToast.showLong(getApplicationContext(), downloadBean.getTitle() + "下载失败,请稍后再试");
                                isDownloading = false;
                                notifyUtil.clear();
                                checkAndDownload();
                            }

                            @Override
                            public void onNext(Object o) {
                                LogUtil.i(TAG, "download success,apkPath：" + downloadBean.getFilePath());
                            }
                        });
        //标记为正在下载
        isDownloading = true;
    }

    /**
     * 安装
     *
     * @param filePath
     */
    public void install(String packageName, String title, String filePath) {
        SilentInstaller silentInstaller = new SilentInstaller();
        int result = silentInstaller.installSilent(DownloadService.this, filePath);
        SPUtil.putString(getApplicationContext(), "filePath" + packageName, filePath);
        if (result == SilentInstaller.INSTALL_SUCCEEDED) {
            LogUtil.i(TAG, "silent installer success");
            CustomToast.showLong(getApplicationContext(), title + "安装成功");
        } else {
            silentInstaller.install(DownloadService.this, new File(filePath));
        }
    }

    /**
     * 静默安装
     */
    public void silenceInstall(final String apkPath) {
        Observable.just(apkPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return new SilentInstaller().installSilent(getApplicationContext(), apkPath) == SilentInstaller.INSTALL_SUCCEEDED;
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "安装成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "安装失败");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    /**
     *
     */
    Subscription subscription = RxBus.getDefault().ofType(NetChangeEvent.class)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<NetChangeEvent>() {
                @Override
                public void call(NetChangeEvent netChangeEvent) {
                    if (NetWorkUtil.NETWORK_TYPE_WIFI.equals(netChangeEvent.getNetworkType())) {
                        enableDownload = true;
                    } else {
                        enableDownload = false;
                    }
                }
            });

    /**
     * 判断网络类型是否可以下载，wifi可下载
     */
    public void checkNetEnableDownload() {
        String netType = NetWorkUtil.getNetworkType(getApplicationContext());
        if (NetWorkUtil.NETWORK_TYPE_WIFI.equals(netType)) {
            enableDownload = true;
        } else {
            enableDownload = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}

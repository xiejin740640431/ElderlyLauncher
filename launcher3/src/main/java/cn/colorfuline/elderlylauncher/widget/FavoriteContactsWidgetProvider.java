package cn.colorfuline.elderlylauncher.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import cn.colorfuline.elderlylauncher.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.elderlylauncher.bean.Contacts;
import cn.colorfuline.elderlylauncher.bean.DownloadBean;
import cn.colorfuline.elderlylauncher.services.DownloadService;
import cn.colorfuline.elderlylauncher.utils.BitmapUtil;
import cn.colorfuline.elderlylauncher.utils.SPUtil;
import cn.colorfuline.elderlylauncher.utils.StorageUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 常用联系人小部件Widget
 * Created by MarcoZhan on 2017/3/3.
 */

public class FavoriteContactsWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "FavoriteContactsWidgetProvider";

    public static final String COLLECTION_VIEW_ACTION = "cn.colorfuline.elderlylauncher.COLLECTION_VIEW_ACTION";

    private List<String> favContacts = new ArrayList<>(); //九宫格集合

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtil.d(TAG, "GridWidgetProvider onUpdate");
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            // 获取AppWidget对应的视图
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_favorite_contacts);
            Contacts contacts = (Contacts) SPUtil.readObjectExt(context.getApplicationContext(), "contact" + appWidgetId);
            if (contacts != null) {
                if (contacts.getIconByte() != null) {
                    rv.setImageViewBitmap(R.id.img_favorite, BitmapUtil.createBitmap(contacts.getIconByte()));
                }
                rv.setTextViewText(R.id.tv, contacts.getUserName());
            }
            Intent fillInIntent = new Intent(context, FavoriteContactsWidgetProvider.class);
            fillInIntent.setAction(COLLECTION_VIEW_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, fillInIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.activity_favorite_contacts, pendingIntent);
            // 调用集合管理器对集合进行更新
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    private static int widgetId;

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, FavoriteContactsWidgetProvider.class));
//            String userName = intent.getStringExtra("name");
//            String phoneNum = intent.getStringExtra("phone");
//            byte[] iconByte = intent.getByteArrayExtra("avatar");
//            Contacts contacts = new Contacts(userName, phoneNum, iconByte);
//            SPUtil.saveObjectExt(context.getApplicationContext(), "contact" + widgetId, contacts);
//            onUpdate(context, appWidgetManager, new int[]{widgetId});
        } else if (action.equals(COLLECTION_VIEW_ACTION)) {
            String title = "通讯录";
            String downloadUrl = "http://10.80.84.236:8080/examples/apks/Addressbook_V1.0.0.apk";
            ArrayList<DownloadBean> list = new ArrayList<>();
            list.add(new DownloadBean("cn.colorfuline.addressbook", title, StorageUtil.FileCachePath.getApkDownLoadPath(context.getApplicationContext(), title), downloadUrl));
            Intent downloadIntent = new Intent(context, DownloadService.class);
            Bundle bundle = new Bundle();
            bundle.putString(DownloadService.EXTRA_ACTION, DownloadService.ACTION_DOWNLOAD);
            bundle.putParcelableArrayList(DownloadService.EXTRA_DOWNLOAD_LIST, list);
            downloadIntent.putExtras(bundle);
            context.startService(downloadIntent);
        }
    }


}

package cn.colorfuline.elderlylauncher.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

 import cn.colorfuline.elderlylauncher.R;

import java.util.ArrayList;
import java.util.List;

import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.elderlylauncher.bean.Contacts;
import cn.colorfuline.elderlylauncher.bean.DownloadBean;
import cn.colorfuline.elderlylauncher.services.DownloadService;
import cn.colorfuline.elderlylauncher.utils.AppUtils;
import cn.colorfuline.elderlylauncher.utils.BitmapUtil;
import cn.colorfuline.elderlylauncher.utils.SPUtil;
import cn.colorfuline.elderlylauncher.utils.StorageUtil;

/**
 * 常用联系人小部件Widget
 * Created by MarcoZhan on 2017/3/3.
 */

public class ColorfulineParentWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "FavoriteContactsWidgetProvider";

    public static final String ACTION_BTN_ACTIVITY_CLICK = "cn.colorfuline.elderlylauncher.colorfuline.parent.ACTION_BTN_ACTIVITY_CLICK";
    public static final String ACTION_BTN_SERVICE_CLICK = "cn.colorfuline.elderlylauncher.colorfuline.parent.ACTION_BTN_SERVICE_CLICK";

    private List<String> favContacts = new ArrayList<>(); //九宫格集合

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtil.d(TAG, "GridWidgetProvider onUpdate");
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            // 获取AppWidget对应的视图
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_colorfuline_parent);
            Intent btnActivityIntent = new Intent(context, ColorfulineParentWidgetProvider.class);
            btnActivityIntent.setAction(ACTION_BTN_ACTIVITY_CLICK);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, btnActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.btn_activity, pendingIntent);

            Intent btnServiceIntent = new Intent(context, ColorfulineParentWidgetProvider.class);
            btnServiceIntent.setAction(ACTION_BTN_SERVICE_CLICK);
            PendingIntent servicePendingIntent = PendingIntent.getBroadcast(context, 0, btnServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.btn_service, servicePendingIntent);
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
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, FavoriteContactsWidgetProvider.class));
//            String userName = intent.getStringExtra("name");
//            String phoneNum = intent.getStringExtra("phone");
//            byte[] iconByte = intent.getByteArrayExtra("avatar");
//            Contacts contacts = new Contacts(userName, phoneNum, iconByte);
//            SPUtil.saveObjectExt(context.getApplicationContext(), "contact" + widgetId, contacts);
//            onUpdate(context, appWidgetManager, new int[]{widgetId});
        } else if (action.equals(ACTION_BTN_ACTIVITY_CLICK)) {
            startActivity(context,0);
        }else if(ACTION_BTN_SERVICE_CLICK.equals(action)){
            startActivity(context,1);
        }
    }

    public void startActivity(Context context,int type){
        Intent intent = AppUtils.getApplicationWithPackageName("com.qicaihong",context.getPackageManager());
        if(intent!=null){
            intent.putExtra("type",type);
//            activityIntent.setClassName("cn.colorfuline.addressbook", "cn.colorfuline.addressbook.activity.AddFavContactsActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            String title = "通讯录";
            String downloadUrl = "http://10.80.84.236:8080/examples/apks/Addressbook_V1.0.0.apk";
            ArrayList<DownloadBean> list = new ArrayList<>();
            list.add(new DownloadBean("cn.colorfuline.addressbook",title,StorageUtil.FileCachePath.getApkDownLoadPath(context.getApplicationContext(), title), downloadUrl));
            Intent downloadIntent = new Intent(context, DownloadService.class);
            Bundle bundle = new Bundle();
            bundle.putString(DownloadService.EXTRA_ACTION, DownloadService.ACTION_DOWNLOAD);
            bundle.putParcelableArrayList(DownloadService.EXTRA_DOWNLOAD_LIST, list);
            downloadIntent.putExtras(bundle);
            context.startService(downloadIntent);
        }
    }


}

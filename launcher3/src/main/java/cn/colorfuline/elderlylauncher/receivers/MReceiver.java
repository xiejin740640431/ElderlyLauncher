package cn.colorfuline.elderlylauncher.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.base.utils.StringUtils;
import cn.colorfuline.elderlylauncher.utils.SPUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.i("MReceiver", "-------->" + action);
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);                                                                                      //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
//            DownloadManager.Query query = new DownloadManager.Query();
//            query.setFilterById(id);
//            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//            Cursor cursor = downloadManager.query(query);
//            String path = null;
//            while (cursor.moveToNext()) {
//                path = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//            }
//            cursor.close();
//            if (!StringUtils.isEmpty(path)) {
//                ApkController.install(context, new File(path));
//            }
        } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {

        } else if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            final String packageName = intent.getData().getSchemeSpecificPart();

        }
    }

}

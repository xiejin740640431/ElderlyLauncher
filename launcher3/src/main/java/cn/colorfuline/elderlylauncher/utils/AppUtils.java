/**
 * Copyright © 2015 zouhuo. All rights reserved.
 *
 * @Title: AppUtils.java
 * @Prject: BangBang
 * @Package: com.bangbang.utils
 * @Description: TODO
 * @author: XJ
 * @date: 2015-8-23 下午6:34:50
 * @version: V1.0
 */
package cn.colorfuline.elderlylauncher.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;


/**
 * @ClassName: AppUtils
 * @Description: 跟App相关的辅助类
 * @author: XJ
 * @date: 2015-8-23 下午6:34:50
 */
public class AppUtils {
    public static final String LOG_TAG = AppUtils.class.getSimpleName();
    /**
     * 判断应用是否安装
     *
     * @param pkgName
     * @return
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * @param packagename
     * @return
     */
    public static Intent getApplicationWithPackageName(String packagename,PackageManager packageManager) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = packageManager.getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return null;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            return intent;
        }
        return null;
    }

    /**
     * 判断无障碍服务是否开启
     *
     * @param mContext
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "cn.colorfuline.remoteassistance/cn.colorfuline.remoteassistance.service.KeyEventService";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(LOG_TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(LOG_TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            Log.v(LOG_TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.v(LOG_TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(LOG_TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(LOG_TAG, "***ACCESSIBILIY IS DISABLED***");
        }
        return accessibilityFound;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param context
     * @return boolean
     * @throws
     * @Title: isApplicationBroughtToBackground
     * @Description: 判断程序是否在后台运行
     * @author XJ
     * @date2015-5-20,上午9:21:23
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        } else {
        }
        return false;
    }

    /**
     * @param context void
     * @throws
     * @Title: open
     * @Description: 打开程序的当前界面
     * @author XJ
     * @date2015-5-15,下午2:33:41
     */
    public static void openAppCurrentActivity(Context context) {
        try {
            Intent intent = new Intent();
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(ACTIVITY_SERVICE);
            List<RunningTaskInfo> task_info = manager.getRunningTasks(20);
            String className = "";
            for (int i = 0; i < task_info.size(); i++) {
                if (context.getPackageName().equals(
                        task_info.get(i).topActivity.getPackageName())) {
                    className = task_info.get(i).topActivity.getClassName();
                    // 这里是指从后台返回到前台 前两个的是关键
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(new ComponentName(context, Class
                            .forName(className)));//
                    // intent.setClass(context,
                    // Class.forName(className));
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    context.startActivity(intent);
                    return;
                }
            }
//			Intent i = new Intent(context, WelcomeActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取app进程名称
     *
     * @param context
     * @param pID
     * @return
     */
    public static String getAppProcessName(Context context, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processName;
    }

    /**
     * 获取设备id
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取imei
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 判断是否有sim卡
     *
     * @return
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 自定义生成UUID，作为唯一标志（不可变）
     * 使用 String.hashCode() 加密序列号
     * 在程序第一次安装运行后生成一个ID（注意：是自己生成，而非系统主动生成的！），但该方式跟设备唯一标识不一样，
     * 它会因为不同的应用程序而产生不同的ID，而不是设备唯一ID。因此经常用来标识在某个应用中的唯一ID（即Installtion ID），或者跟踪应用的安装数量。
     *
     * @param context
     * @return
     */
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
    */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断《《某个应用程序》》是否在后台运行
     * appPackageName==applicationId
     */
    public static boolean isBackgroundRunning(Context context,String appPackageName) {
        String processName = appPackageName;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);

        if (activityManager == null) return false;
       // get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != IMPORTANCE_FOREGROUND && process.importance != IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState) return true;
                else return false;
            }
        }
        return false;
    }

    /**
     * 没有安装父母版就去下载
     */
    public static void downloadParentApp(Context context){
        
    }
}

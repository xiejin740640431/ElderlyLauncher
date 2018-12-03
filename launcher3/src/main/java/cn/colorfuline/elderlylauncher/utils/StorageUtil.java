package cn.colorfuline.elderlylauncher.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Method;

import cn.colorfuline.base.utils.LogUtil;

/**
 * 存储管理工具类
 *
 * @author Administrator
 */
public class StorageUtil {
    static final String LOG_TAG = StorageUtil.class.getSimpleName();
    private static String BASE_CACHE_NAME = "colorfuline/elderlyLauncher";
    private static String imgCachePath;
    private static String SDCachePath;

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 获取存储路径
     *
     * @return
     */
    public static String getStoragePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        StorageManager storageManager = (StorageManager) context
                .getApplicationContext().getSystemService(
                        Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod(
                    "getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                File file = new File(((String[]) invoke)[i]);
                if (file != null && file.listFiles() != null) {
                    return file.getAbsolutePath();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * @param context
     * @return
     * @Title: getSDPath
     * @Description: 获取sd卡路径
     * @return: String
     */
    public static String getSDPath(Context context) {
        if (SDCachePath == null) {
            SDCachePath = getStoragePath(context) + File.separator + BASE_CACHE_NAME;
            LogUtil.i(LOG_TAG, SDCachePath);
            if (!new File(SDCachePath).exists())
                new File(SDCachePath).mkdir();
        }
        return SDCachePath;
    }

    /**
     * @param @param  context
     * @param @param  dirName
     * @param @return
     * @return String
     * @throws
     * @Title: getSDPath
     * @Description: 根据文件夹名称获取sd卡缓存路径
     */
    public static String getSDPath(Context context, String dirName) {
        SDCachePath = getStoragePath(context) + File.separator + BASE_CACHE_NAME
                + File.separator + dirName;
        LogUtil.i(LOG_TAG, SDCachePath);
        if (!new File(SDCachePath).exists())
            new File(SDCachePath).mkdirs();
        return SDCachePath;
    }

    /**
     * @param @param  context
     * @param @return
     * @return String
     * @throws
     * @Title: getAppCachePath
     * @Description: 获取应用分配的缓存路径
     */
    public static String getAppCachePath(Context context) {
        if (imgCachePath == null) {
            imgCachePath = context.getCacheDir() + File.separator + BASE_CACHE_NAME;
            LogUtil.i(LOG_TAG, imgCachePath);
            if (!new File(imgCachePath).exists())
                new File(imgCachePath).mkdir();
        }
        return imgCachePath;
    }

    /**
     * @param @param  context
     * @param @param  dirName
     * @param @return
     * @return String
     * @throws
     * @Title: getAppCachePath
     * @Description: 根据文件夹名返回路径（没有则创建）
     */
    public static String getAppCachePath(Context context, String dirName) {
        imgCachePath = context.getCacheDir() + File.separator + BASE_CACHE_NAME
                + File.separator + dirName;
        LogUtil.i(LOG_TAG, imgCachePath);
        if (!new File(imgCachePath).exists())
            new File(imgCachePath).mkdir();
        return imgCachePath;
    }

    /**
     * @return
     * @Title: getCachePath
     * @Description: 获取缓存路径
     * @return: String
     */
    public static String getCachePath(Context context) {
        if (imgCachePath == null) {
            imgCachePath = getSDPath(context);
            if (new File(imgCachePath).exists()) {
                return imgCachePath;
            }
            imgCachePath = context.getCacheDir() + File.separator + BASE_CACHE_NAME;
            LogUtil.i(LOG_TAG, imgCachePath);
            if (!new File(imgCachePath).exists())
                new File(imgCachePath).mkdir();
        }
        return imgCachePath;
    }

    /**
     * @param @param  context
     * @param @param  dirName
     * @param @return
     * @return String
     * @throws
     * @Title: getCachePath
     * @Description: 根据文件夹名称获取缓存路径
     */
    public static String getCachePath(Context context, String dirName) {
        imgCachePath = getSDPath(context, dirName);
        if (new File(imgCachePath).exists()) {
            return imgCachePath;
        }
        imgCachePath = context.getCacheDir() + File.separator + BASE_CACHE_NAME;
        LogUtil.i(LOG_TAG, imgCachePath);
        if (!new File(imgCachePath).exists())
            new File(imgCachePath).mkdirs();
        return imgCachePath;
    }

    /**
     * @author 传奇丶小胖
     * @ClassName: FileCachePath
     * @Description: 获取不同类型的缓存路径
     * @date 2015-7-29 下午9:40:28
     */
    public static class FileCachePath {
        /**
         * @param @param  context
         * @param @return
         * @return String
         * @throws
         * @Title: getUserCachePath
         * @Description: 用户头像缓存路径
         */
        public static String getUserCachePath(Context context) {
            return getCachePath(context, "userHeadImg");
        }

        public static String getActiviteImgCachePath(Context context) {
            return getCachePath(context, "activiteImgs");
        }

        public static String getUpLoadImagesCachePath(Context context) {
            return getCachePath(context, "uploadImages");
        }

        public static String getImagesCachePath(Context context) {
            return getCachePath(context, "images");
        }

        public static String getAppDownLoadPath(Context context) {
            return getCachePath(context, "apks");
        }

        public static String getApkDownLoadPath(Context context,String fileName){
            return getAppDownLoadPath(context) +File.separator+ fileName + ".apk";
        }

        public static String getDownLoadPath(Context context) {
            return getCachePath(context, "download");
        }

        public static String getChatImgPath(Context context) {
            return getCachePath(context, "chat");
        }

        public static String getRecorderPath(Context context) {
            return getCachePath(context, "revorder");
        }
    }
}

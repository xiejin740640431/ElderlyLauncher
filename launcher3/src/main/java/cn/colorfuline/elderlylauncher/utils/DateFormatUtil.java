package cn.colorfuline.elderlylauncher.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/21.
 */
public class DateFormatUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_NO_SECOUND = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public static String getCurrentDate(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 格式化日期
     * @param date
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String formatDate(String date,String oldPattern,String newPattern){
        try{
            SimpleDateFormat oldFormat = new SimpleDateFormat(oldPattern);
            SimpleDateFormat newFormat = new SimpleDateFormat(newPattern);
            return newFormat.format(oldFormat.parse(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static Date formatDate(String date,String pattern){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Date();
    }
}

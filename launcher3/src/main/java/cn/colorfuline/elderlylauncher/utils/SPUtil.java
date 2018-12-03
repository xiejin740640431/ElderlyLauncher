package cn.colorfuline.elderlylauncher.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * sharepreference工具类
 */
public class SPUtil {
	public static String PREFERENCE_NAME = "colorfuline_elderlylauncher";
	public static String PREFERENCE_OBJECT_NAME = "colorfuline_elderlylauncher_obj";

	/**
	 * 获取默认的SP
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return context == null ? null : context.getSharedPreferences(
				PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 根据preferenceName获取SP
	 * @param context
	 * @param preferenceName
	 * @return
	 */
	public static SharedPreferences getSharePreferencesByName(Context context, String preferenceName){
		return context == null ? null : context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
	}

	/**
	 * put string preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The news value for the preference
	 * @return True if the news values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putString(Context context, String key, String value) {
		return putString(context,PREFERENCE_NAME,key,value);
	}

	/**
	 * 根据preferenceName存
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putString(Context context, String preferenceName, String key, String value){
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	/**
	 * get string preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or null. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 * @see #getString(Context, String, String)
	 */
	public static String getString(Context context, String key) {
		return getString(context, key, null);
	}

	/**
	 * get string preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 */
	public static String getString(Context context, String key,
								   String defaultValue) {
		return getString(context, PREFERENCE_NAME, key, defaultValue);
	}

	/**
	 *
	 * @param context
	 * @param preferenceName
	 * @param key
     * @return
     */
	public static String getStringByPreferenceName(Context context, String preferenceName, String key){
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		return settings.getString(key, null);
	}
	/**
	 * 根据preferenceName获取String
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Context context, String preferenceName, String key, String defaultValue){
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	/**
	 * put int preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The news value for the preference
	 * @return True if the news values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putInt(Context context, String key, int value) {
		return putInt(context, PREFERENCE_NAME, key, value);
	}

	/**
	 * 根据preferenceName存
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putInt(Context context, String preferenceName, String key, int value) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	/**
	 * get int preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 * @see #getInt(Context, String, int)
	 */
	public static int getInt(Context context, String key) {
		return getInt(context, key, -1);
	}

	/**
	 * get int preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 */
	public static int getInt(Context context, String key, int defaultValue) {
		return getInt(context, PREFERENCE_NAME, key, defaultValue);
	}

	/**
	 * 根据preferenceName
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	private static int getInt(Context context, String preferenceName, String key, int defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}
	/**
	 * put long preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The news value for the preference
	 * @return True if the news values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putLong(Context context, String key, long value) {
		return putLong(context, PREFERENCE_NAME, key, value);
	}

	/**
	 * 根据preferenceName存
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putLong(Context context, String preferenceName, String key, long value) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	/**
	 * get long preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 * @see #getLong(Context, String, long)
	 */
	public static long getLong(Context context, String key) {
		return getLong(context, key, -1);
	}

	/**
	 * get long preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 */
	public static long getLong(Context context, String key, long defaultValue) {
		return getLong(context, PREFERENCE_NAME, key, defaultValue);
	}

	/**
	 * 根据preferenceName
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	private static long getLong(Context context, String preferenceName, String key, long defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}
	/**
	 * put float preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The news value for the preference
	 * @return True if the news values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putFloat(Context context, String key, float value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENCE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * get float preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 * @see #getFloat(Context, String, float)
	 */
	public static float getFloat(Context context, String key) {
		return getFloat(context, key, -1);
	}

	/**
	 * get float preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 */
	public static float getFloat(Context context, String key, float defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENCE_NAME, Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * put boolean preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The news value for the preference
	 * @return True if the news values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putBoolean(Context context, String key, boolean value) {
		return putBoolean(context, PREFERENCE_NAME, key, value);
	}

	/**
	 * 根据preferenceName存
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putBoolean(Context context, String preferenceName, String key, boolean value) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

    /**
     *
     * @param context
     * @param preferenceName
     * @param key
     * @return
     */
    public static boolean putBoolean(Context context, String preferenceName, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, false);
        return editor.commit();
    }
	/**
	 * get boolean preferences, welcome_default is false
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 * @see #getBoolean(Context, String, boolean)
	 */
	public static boolean getBoolean(Context context, String key) {
		return getBoolean(context, key, false);
	}

	/**
	 * get boolean preferences
	 *
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defaultValue) {
		return getBoolean(context, PREFERENCE_NAME, key, defaultValue);
	}

	/**
	 * 根据preferenceName
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String preferenceName, String key,
									 boolean defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(
				preferenceName, Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}

    public static boolean getBoolean(Context context, String preferenceName, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }
	/**
	 * @Title: putBytes
	 * @Description: 保存字节数组
	 * @param context
	 * @param key
	 * @param bytes
	 * @return
	 * @return: boolean
	 */
	public static boolean putBytes(Context context, String key, byte[] bytes){
		return putBytes(context, PREFERENCE_NAME, key, bytes);
	}

	/**
	 * 根据preferenceName存
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param bytes
	 * @return
	 */
	private static  boolean putBytes(Context context, String preferenceName, String key, byte[] bytes){
		try {
			SharedPreferences settings = context.getSharedPreferences(
					preferenceName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(key, new String(bytes,"ISO-8859-1"));
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * @Title: getStrByBytes
	 * @Description: 获取字节数组并转换成字符串
	 * @param context
	 * @param key
	 * @return
	 * @return: String
	 */
	public static byte[] getBytes(Context context, String key){
		return getBytes(context,PREFERENCE_NAME,key);
	}

	/**
	 * 根据preferenceName
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @return
	 */
	private static byte[] getBytes(Context context, String preferenceName, String key){
		try {
			SharedPreferences settings = context.getSharedPreferences(
					preferenceName, Context.MODE_PRIVATE);
			return settings.getString(key, "").getBytes("ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *
	 * <b>@Description:<b>base64保存图片<br/>
	 * <b>@param context
	 * <b>@param key
	 * <b>@param obj<b>void<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-7-25-下午5:47:09<br/>
	 */
	public static boolean saveBitmap(Context context, String key, Bitmap obj) {
		try {
			boolean flag = false;
			SharedPreferences.Editor sharedata = context.getSharedPreferences(PREFERENCE_OBJECT_NAME, 0).edit();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			obj.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
			String imageString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
			sharedata.putString(key, imageString);
			flag = sharedata.commit();
			byteArrayOutputStream.close();
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *
	 * <b>@Description:<b>读取bitmap<br/>
	 * <b>@param context
	 * <b>@param key
	 * <b>@return<b>Bitmap<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-7-25-下午5:46:57<br/>
	 */
	public static synchronized Bitmap readBitmap(Context context, String key) {
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_OBJECT_NAME, 0);
			String string = sharedPreferences.getString(key, "");
			byte[] imageBytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
			return BitmapFactory.decodeStream(byteArrayInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 *
	 * <b>@Description:<b>必须实现序列接口<br/>
	 * <b>@param context
	 * <b>@param key
	 * <b>@param obj<b>void<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-7-25-下午5:46:46<br/>
	 */
	public static boolean saveObjectExt(Context context, String key, Serializable obj) {
		return saveObjectExt(context,PREFERENCE_OBJECT_NAME,key,obj);
	}

	/**
	 *
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @param obj
	 * @return
	 */
	public static boolean saveObjectExt(Context context, String preferenceName, String key, Serializable obj) {
		try {
			// 保存对象
			SharedPreferences.Editor sharedata = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit();
			sharedata.putString(key, getObjBase64Str(obj));
			return sharedata.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 *
	 * <b>@Description:<b>读取一个对象<br/>
	 * <b>@param context
	 * <b>@param key
	 * <b>@return<b>Object<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-7-25-下午5:46:32<br/>
	 */
	public static synchronized Object readObjectExt(Context context, String key) {
		return readObjectExt(context,PREFERENCE_OBJECT_NAME,key);
	}

	/**
	 *
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @return
	 */
	public static synchronized Object readObjectExt(Context context, String preferenceName, String key) {
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, 0);
			String base64Str = sharedPreferences.getString(key, "");
			if(!TextUtils.isEmpty(base64Str)){
				byte[] objBytes = Base64.decode(base64Str.getBytes(), Base64.DEFAULT);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objBytes);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				objectInputStream.close();
				return objectInputStream.readObject();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *
	 * <b>@Description:<b>得到一个对象的base64字符串<br/>
	 * <b>@param obj <b>@return<b>String<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-7-25-下午5:14:19<br/>
	 */
	public static String getObjBase64Str(Serializable obj) {
		try {
			// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// 然后将得到的字符数据装载到ObjectOutputStream
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
			objectOutputStream.writeObject(obj);
			// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
			String objBase64Str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
			// 关闭objectOutputStream
			objectOutputStream.close();
			return objBase64Str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据key判断是否存在
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key){
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENCE_NAME, Context.MODE_PRIVATE);
		return settings.contains(key);
	}

	/**
	 * 判断对象是否存在
	 * @param context
	 * @param key
     * @return
     */
	public static boolean objContains(Context context, String key){
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENCE_OBJECT_NAME, Context.MODE_PRIVATE);
		return settings.contains(key);
	}

    /**
     *
     * @param context
     * @param preferenceName
     * @param key
     * @return
     */
    public static boolean objContains(Context context, String preferenceName, String key){
        SharedPreferences settings = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return settings.contains(key);
    }
	/**
	 * @Title: removeObj
	 * @Description: 删除对象缓存
	 * @param context
	 * @param key
	 * @return
	 * @return: boolean
	 */
	public static boolean removeObj(Context context, String key){
		return removeObj(context,PREFERENCE_OBJECT_NAME,key);
	}

	/**
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean removeObj(Context context, String preferenceName, String key){
		try {
			SharedPreferences settings = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.remove(key);
			editor.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * <b>@Description:<b>根据key来删除<br/>
	 * <b>@param context
	 * <b>@param key
	 * <b>@return<b>boolean<br/>
	 * <b>@Author:<b>ccy<br/>
	 * <b>@Since:<b>2014-8-21-上午11:48:12<br/>
	 */
	public static boolean removeKey(Context context, String key){
		return removeKey(context,PREFERENCE_NAME,key);
	}

	/**
	 * @param context
	 * @param preferenceName
	 * @param key
	 * @return
	 */
	private static boolean removeKey(Context context, String preferenceName, String key){
		try {
			SharedPreferences settings = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.remove(key);
			editor.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

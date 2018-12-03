package cn.colorfuline.base.utils;

/**
 * @Description:  系统日志工具  统一用这个类来控制日志输出
 * @Author: ccy
 * @Date:2014-6-25 上午11:19:03 
 */
import android.util.Log;



public class LogUtil {

	public static boolean isDebug(){
		return true;
	}

	public static void d(String tag, String msg) {
		if (isDebug()) {
			StackTraceElement stackTrace = Thread.currentThread()
					.getStackTrace()[3];
			String fileInfo = stackTrace.getFileName() + "("
					+ stackTrace.getLineNumber() + ") "
					+ stackTrace.getMethodName();
			Log.d(tag, fileInfo + ": " + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isDebug()) {
			StackTraceElement stackTrace = Thread.currentThread()
					.getStackTrace()[3];
			String fileInfo = stackTrace.getFileName() + "("
					+ stackTrace.getLineNumber() + ") "
					+ stackTrace.getMethodName();
			Log.i(tag, fileInfo + ": " + msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug()) {
			StackTraceElement stackTrace = Thread.currentThread()
					.getStackTrace()[3];
			String fileInfo = stackTrace.getFileName() + "("
					+ stackTrace.getLineNumber() + ") "
					+ stackTrace.getMethodName();
			Log.e(tag, fileInfo + ": " + msg);
		}
	}

	public static void w(String tag, String msg) {
		if (isDebug()) {
			StackTraceElement stackTrace = Thread.currentThread()
					.getStackTrace()[3];
			String fileInfo = stackTrace.getFileName() + "("
					+ stackTrace.getLineNumber() + ") "
					+ stackTrace.getMethodName();
			Log.w(tag, fileInfo + ": " + msg);
		}
	}

	public static void v(String tag, String msg) {
		if (isDebug()) {
			StackTraceElement stackTrace = Thread.currentThread()
					.getStackTrace()[3];
			String fileInfo = stackTrace.getFileName() + "("
					+ stackTrace.getLineNumber() + ") "
					+ stackTrace.getMethodName();
			Log.v(tag, fileInfo + ": " + msg);
		}
	}

	public static String getStackTraceMsg() {
		StackTraceElement stackTrace = Thread.currentThread()
				.getStackTrace()[3];
		String fileInfo = stackTrace.getFileName() + "("
				+ stackTrace.getLineNumber() + ") "
				+ stackTrace.getMethodName();
		return fileInfo;
	}
}

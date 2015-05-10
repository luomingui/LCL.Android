package lcl.android.utility;

import android.util.Log;

public class LogUtils {

	static String logFlg = "lcl";

	public static void Error(String tag, Exception e) {
		Log.e(logFlg, "Error:" + tag + e.toString());
	}

	public static void Info(String msg) {
		Log.e(logFlg, "Info:" + msg);
	}
}

package lcl.android.utility;

import java.lang.ref.WeakReference;

import lcl.android.ui.ImageZoomDialog;
import lcl.android.ui.Main;
import lcl.android.ui.Works;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UIHelper {
	private static WeakReference<Toast> prevToast = new WeakReference<Toast>(
			null);
	private static WeakReference<Context> prevContext = new WeakReference<Context>(
			null);

	/**
	 * 显示首页
	 * 
	 * @param activity
	 */
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, Main.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 显示作品
	 * 
	 * @param activity
	 */
	public static void showWork(Activity activity) {
		Intent intent = new Intent(activity, Works.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 调用系统安装了的应用分享
	 * 
	 * @param context
	 * @param title
	 * @param url
	 */
	public static void showShareMore(Activity context, final String title,
			final String url) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
		intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
		context.startActivity(Intent.createChooser(intent, "选择分享"));
	}

	/**
	 * 添加网页的点击图片展示支持
	 */
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	public static void addWebImageShow(final Context cxt, WebView wv) {
		wv.getSettings().setJavaScriptEnabled(true);
		wv.addJavascriptInterface(new OnWebViewImageListener() {

			@Override
			public void onImageClick(String bigImageUrl) {
				if (bigImageUrl != null)
					UIHelper.showImageZoomDialog(cxt, bigImageUrl);
			}
		}, "mWebViewImageListener");
	}

	public static void showImageZoomDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageZoomDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void showToast(Context cont, String msg) {
		showToast(cont, msg, Toast.LENGTH_SHORT);
	}

	public static void showToast(Context cont, int msg) {
		showToast(cont, cont.getString(msg), Toast.LENGTH_SHORT);
	}

	public static void showToast(Context cont, int msg, int time) {
		showToast(cont, cont.getString(msg), time);
	}

	public static void showToast(Context cont, String msg, int time) {
		if (prevContext.get() != null && prevToast.get() != null
				&& prevContext.get() == cont) {
			Toast toast = prevToast.get();
			toast.setText(msg);
			toast.setDuration(time);
			toast.show();
			return;
		}
		Toast toast = Toast.makeText(cont, msg, time);
		toast.show();
		prevContext = new WeakReference<Context>(cont);
		prevToast = new WeakReference<Toast>(toast);
	}

	/**
	 * 点击返回监听事件
	 * 
	 * @param activity
	 * @return
	 */
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url) {
		try {
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			showToast(context, "提示", 500);
		}
	}
}

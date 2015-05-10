package lcl.android.sms;

import java.util.List;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * class name：SmsReceiver 监听类<BR>
 * class description：数据库改变监听类<BR>
 * PS：当数据改变的时候，执行里面才change方法<BR>
 * Date:2012-3-16<BR>
 * 怎么注册这个监听呢，只需：<BR>
 * ContentObserver co = new SmsReceiver(new Handler(),AndroidUtilActivity.this);
 * this.getContentResolver().registerContentObserver
 * (Uri.parse("content://sms/"), true, co);
 * 
 * @version 1.00
 * @author CODYY)peijiangping
 */
public class SmsReceiver extends ContentObserver {
	/**
	 * Activity对象
	 */
	private Activity activity;
	private List<SmsInfo> infos;

	public SmsReceiver(Handler handler, Activity activity) {
		super(handler);
		this.activity = activity;
	}

	@Override
	public void onChange(boolean selfChange) {
		Uri uri = Uri.parse(AllFinalInfo.SMS_URI_INBOX);// 设置一个uri来查看各种类别短信内容
		SmsContent smscontent = new SmsContent(activity, uri);
		infos = smscontent.getSmsInfo();
		System.out.println(infos.get(1).getSmsbody());
		super.onChange(selfChange);
	}
}

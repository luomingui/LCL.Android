package lcl.android.sms;

import java.util.List;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * class name��SmsReceiver ������<BR>
 * class description�����ݿ�ı������<BR>
 * PS�������ݸı��ʱ��ִ�������change����<BR>
 * Date:2012-3-16<BR>
 * ��ôע����������أ�ֻ�裺<BR>
 * ContentObserver co = new SmsReceiver(new Handler(),AndroidUtilActivity.this);
 * this.getContentResolver().registerContentObserver
 * (Uri.parse("content://sms/"), true, co);
 * 
 * @version 1.00
 * @author CODYY)peijiangping
 */
public class SmsReceiver extends ContentObserver {
	/**
	 * Activity����
	 */
	private Activity activity;
	private List<SmsInfo> infos;

	public SmsReceiver(Handler handler, Activity activity) {
		super(handler);
		this.activity = activity;
	}

	@Override
	public void onChange(boolean selfChange) {
		Uri uri = Uri.parse(AllFinalInfo.SMS_URI_INBOX);// ����һ��uri���鿴��������������
		SmsContent smscontent = new SmsContent(activity, uri);
		infos = smscontent.getSmsInfo();
		System.out.println(infos.get(1).getSmsbody());
		super.onChange(selfChange);
	}
}

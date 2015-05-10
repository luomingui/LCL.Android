package lcl.android.sms;

import java.util.Collections;
import java.util.List;

import lcl.android.utility.LogUtils;
import android.app.Activity;
import android.net.Uri;

public class SmsHelper {
	@SuppressWarnings("unchecked")
	public static String GetSmsInfoStr(Activity activity) {
		StringBuilder sb = new StringBuilder();
		try {
			Uri uri = Uri.parse(AllFinalInfo.SMS_URI_INBOX);
			SmsContent sc = new SmsContent(activity, uri);
			List<SmsInfo> infos = sc.getSmsInfo();

			SmsInfoComparator comparator = new SmsInfoComparator();
			Collections.sort(infos, comparator);

			for (int i = 0; i < infos.size(); i++) {
				SmsInfo info = infos.get(i);
				sb.append(info.toString());
				sb.append('\n');
			}
		} catch (Exception e) {
			LogUtils.Error("SmsHelper GetSmsInfoStr  " + sb + " sms error : ",
					e);
		}
		return sb.toString();
	}
}

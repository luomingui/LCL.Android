package lcl.android.sms;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class SmsInfoComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		int flag = 0;
		try {
			SmsInfo user0 = (SmsInfo) arg0;
			SmsInfo user1 = (SmsInfo) arg1;
			flag = user0.getPhoneNumber().compareTo(user1.getPhoneNumber());
			if (flag == 0) {
				return user0.getName().compareTo(user1.getName());
			} else {
				return flag;
			}
		} catch (Exception e) {
			return flag;
		}
	}
}

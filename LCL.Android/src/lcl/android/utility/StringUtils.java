package lcl.android.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

public class StringUtils {
	/**
	 * ����ת����
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * �жϸ����ַ����Ƿ�հ״��� �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ��� �������ַ���Ϊnull����ַ���������true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * �ַ���ת����ֵ
	 * 
	 * @param b
	 * @return ת���쳣���� false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * �ַ���ת����
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * Stringת��Ϊʱ��
	 * 
	 * @param str
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date ParseDate(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date addTime = null;
		try {
			addTime = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return addTime;
	}

	/**
	 * ������ת��Ϊ�ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String ParseDateToString(Date date) {
		return ParseDateToString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ������ת��Ϊ�ַ��������أ�
	 * 
	 * @param date
	 * @param format
	 *            :ʱ���ʽ���������yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String ParseDateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(date);
	}

	/**
	 * ��UMTʱ��ת��Ϊ����ʱ��
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date ParseUTCDate(String str) {
		// ��ʽ��2012-03-04T23:42:00+08:00
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
		try {
			Date date = formatter.parse(str);

			return date;
		} catch (ParseException e) {
			// ��ʽ��Sat, 17 Mar 2012 11:37:13 +0000
			// Sat, 17 Mar 2012 22:13:41 +0800
			try {
				SimpleDateFormat formatter2 = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss Z", Locale.CHINA);
				Date date2 = formatter2.parse(str);

				return date2;
			} catch (ParseException ex) {
				return null;
			}
		}
	}

	/**
	 * ��ʱ��ת��Ϊ����
	 * 
	 * @param datetime
	 * @return
	 */
	public static String DateToChineseString(Date datetime) {
		Date today = new Date();
		long seconds = (today.getTime() - datetime.getTime()) / 1000;

		long year = seconds / (24 * 60 * 60 * 30 * 12);// �������
		long month = seconds / (24 * 60 * 60 * 30);// �������
		long date = seconds / (24 * 60 * 60); // ��������
		long hour = (seconds - date * 24 * 60 * 60) / (60 * 60);// ����Сʱ��
		long minute = (seconds - date * 24 * 60 * 60 - hour * 60 * 60) / (60);// ���ķ�����
		long second = (seconds - date * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);// ��������

		if (year > 0) {
			return year + "��ǰ";
		}
		if (month > 0) {
			return month + "��ǰ";
		}
		if (date > 0) {
			return date + "��ǰ";
		}
		if (hour > 0) {
			return hour + "Сʱǰ";
		}
		if (minute > 0) {
			return minute + "����ǰ";
		}
		if (second > 0) {
			return second + "��ǰ";
		}
		return "δ֪ʱ��";
	}
}

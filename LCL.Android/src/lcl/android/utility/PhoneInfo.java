package lcl.android.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

/*
 * ��ȡandroid�ֻ�������Ϣ
 * */
public class PhoneInfo {

	@SuppressWarnings("deprecation")
	public String GetPhoneInfo() {

		// BOARD ����
		String phoneInfo = "BOARD: " + android.os.Build.BOARD;
		// BRAND ��Ӫ��
		phoneInfo += ", BRAND: " + android.os.Build.BRAND;
		phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
		// DEVICE ����
		phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
		// DISPLAY ��ʾ
		phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
		// ָ��
		phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
		// HARDWARE Ӳ��
		phoneInfo += ", HOST: " + android.os.Build.HOST;
		phoneInfo += ", ID: " + android.os.Build.ID;
		// MANUFACTURER ��������
		phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
		// MODEL ����
		phoneInfo += ", MODEL: " + android.os.Build.MODEL;
		phoneInfo += ", PRODUCT: " + android.os.Build.PRODUCT;
		phoneInfo += ", RADITAGSO: " + android.os.Build.TAGS;
		phoneInfo += ", TIME: " + android.os.Build.TIME;
		phoneInfo += ", TYPE: " + android.os.Build.TYPE;
		phoneInfo += ", USER: " + android.os.Build.USER;
		// VERSION.RELEASE �̼��汾
		phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
		phoneInfo += ", VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
		// VERSION.INCREMENTAL �����汾
		phoneInfo += ", VERSION.INCREMENTAL: "
				+ android.os.Build.VERSION.INCREMENTAL;
		// VERSION.SDK SDK�汾
		phoneInfo += ", VERSION.SDK: " + android.os.Build.VERSION.SDK;
		phoneInfo += ", VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT;

		return phoneInfo;
	}

	/**
	 * �ֻ�CPU��Ϣ
	 */
	@SuppressWarnings("unused")
	private String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-cpu�ͺ� //2-cpuƵ��
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		Log.i("text", "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
		return cpuInfo;
	}
}

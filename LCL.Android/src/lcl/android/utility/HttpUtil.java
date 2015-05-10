package lcl.android.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HttpUtil {
	/**
	 * ��ȡ������
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public static InputStream GetInputStreamByUrl(String url) {
		InputStream is = null;
		try {
			URL myUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream();
		} catch (IOException e) {
			Log.i("debug", "RssRead GetInputStreamByUrl : " + e.getMessage());
		}
		return is;
	}

	/**
	 * Get���󷽷�
	 * 
	 * @param path
	 *            �����ַ
	 * @param params
	 *            �������
	 * @return ��������Ľ��
	 */
	public static String geturl(String path, Map<String, String> params) {
		String strResult = "0";
		StringBuilder sb = new StringBuilder(path);
		HttpURLConnection conn = null;
		int stateCode = 0;
		try {
			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(entry.getKey()).append('=')
							.append(entry.getValue()).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(10 * 1000); // ���ù���ʱ��Ϊ10��
			conn.setRequestMethod("GET"); // ���÷���ΪGET
			conn.connect();
			stateCode = conn.getResponseCode();
			if (stateCode == HttpURLConnection.HTTP_OK) {
				// ����ɹ�����
				Scanner scanner = new Scanner(conn.getInputStream(), "utf-8");// ��utf-8��ʽ���գ�������������
				StringBuffer str = new StringBuffer();
				while (scanner.hasNextLine()) {
					str.append(scanner.nextLine());
					str.append("\n");
				}
				strResult = str.toString();
			}
		} catch (Exception e) {
			LogUtils.Error("HtmlRegexpUtil geturl error ��", e);
		} finally {
			conn.disconnect();
		}
		return strResult;
	}

	/**
	 * �򵥵�post�������ޱ��롢��ʱ����
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            ����
	 * @return ��������Ľ��
	 */
	public static String posturl(String url, Map<String, String> params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			if (params != null) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
							entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
			HttpResponse response = httpClient.execute(httpPost);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
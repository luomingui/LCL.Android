package lcl.android.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

public class AppConfig {
	private final static String APP_CONFIG = "config";
	public static final String LOCAL_PATH = "file:///android_asset/";// ����html
	public static final String Blog_Url = "http://feed.cnblogs.com/blog/u/54064/rss";
	public static final String mimeType = "text/html";
	public static final String encoding = "utf-8";// ȫ�ֱ��뷽ʽ

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_CHECKUP = "perf_checkup";
	public final static String SAVE_IMAGE_PATH = "save_image_path";
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";

	public static final String DB_FILE_NAME = "cnblogs_db";// ���ݿ��ļ���
	public static final String DB_BLOG_TABLE = "BlogList";// ���ݿ����
	public static final int BLOG_PAGE_SIZE = 10;// ���ͷ�ҳ����
	public static final String URL_GET_BLOG_LIST = "http://www.cnblogs.com/luomingui/default.html?page={pageIndex}";// ����ҳ�루��1��ʼ)

	@SuppressLint("NewApi")
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "OSChina"
			+ File.separator;

	private Context mContext;
	private static AppConfig appConfig;

	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// ��ȡapp_configĿ¼�µ�config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator
					+ APP_CONFIG);
			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {

			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// ��config����(�Զ���)app_config��Ŀ¼��
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
}

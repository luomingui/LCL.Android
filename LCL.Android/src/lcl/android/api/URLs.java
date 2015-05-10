package lcl.android.api;

import java.io.Serializable;

/**
 * 接口URL实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class URLs implements Serializable {
	public final static String HOST = "files.cnblogs.com/luomingui/";
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";

	private final static String URL_API_HOST = HTTP + HOST;

	public final static String UPDATE_VERSION = URL_API_HOST
			+ "//MobileAppVersion.xml";
}

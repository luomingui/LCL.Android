package lcl.android.api;

import java.io.InputStream;
import java.util.List;

import lcl.android.core.AppConfig;
import lcl.android.core.AppContext;
import lcl.android.core.Update;
import lcl.android.rss.Message;
import lcl.android.rss.RssRead;
import lcl.android.utility.HttpUtil;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

/**
 * API�ͻ��˽ӿڣ����ڷ�����������
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ApiClient {
	/**
	 * ���汾����
	 * 
	 * @param url
	 * @return
	 */
	public static Update checkVersion(AppContext appContext) throws Exception {
		try {
			return Update.parse(HttpUtil
					.GetInputStreamByUrl(URLs.UPDATE_VERSION));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ��ȡ���������б�
	 * 
	 * @param url
	 * @return
	 */
	public static List<Message> GetBlogArticleList(View v, boolean isLocal) {
		RssRead rssread = new RssRead();
		List<Message> msges;
		try {
			if (isLocal) {
				AssetManager assetManager = v.getContext().getAssets();
				InputStream inputStream = assetManager.open("cnblogs.xml");
				msges = rssread.ReadloadRss(inputStream);
			} else {
				InputStream is = HttpUtil
						.GetInputStreamByUrl(AppConfig.Blog_Url);
				msges = rssread.ReadloadRss(is);
			}
		} catch (Exception e) {
			InputStream is = HttpUtil.GetInputStreamByUrl(AppConfig.Blog_Url);
			msges = rssread.ReadloadRss(is);
		}
		return msges;
	}

	/**
	 * ��ȡ�����շ������б�
	 * 
	 * @param url
	 * @return
	 */
	public static List<Message> GetClcsArticleList(Context context,
			boolean isLocal) {
		RssRead rssread = new RssRead();
		List<Message> msges;
		try {
			if (isLocal) {
				AssetManager assetManager = context.getAssets();
				InputStream inputStream = assetManager
						.open("Works/CLCS/clcs.xml");
				msges = rssread.ReadloadRss(inputStream);
			} else {
				InputStream is = HttpUtil
						.GetInputStreamByUrl(AppConfig.Blog_Url);
				msges = rssread.ReadloadRss(is);
			}

		} catch (Exception e) {
			InputStream is = HttpUtil.GetInputStreamByUrl(AppConfig.Blog_Url);
			msges = rssread.ReadloadRss(is);
		}
		return msges;
	}
}

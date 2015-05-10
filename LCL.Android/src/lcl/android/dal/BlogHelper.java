package lcl.android.dal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import lcl.android.core.AppConfig;
import lcl.android.entity.BlogEntity;
import lcl.android.parser.BlogListXmlParser;
import lcl.android.parser.BlogXmlParser;
import lcl.android.utility.HttpUtil;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

/**
 * Blog操作类
 * 
 * @author walkingp
 * 
 */
public class BlogHelper extends DefaultHandler {
	/**
	 * 离线下载
	 * 
	 * @param top
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<BlogEntity> DownloadOfflineBlogList(int top) {
		int pageSize = top / AppConfig.BLOG_PAGE_SIZE;
		int lastNum = pageSize % AppConfig.BLOG_PAGE_SIZE;

		List<BlogEntity> listBlogs = null;
		// 下载前几页
		for (int i = 0; i < pageSize; i++) {
			List<BlogEntity> list = GetBlogList(i);

			listBlogs.addAll(list);
		}
		// 下载剩余内容
		List<BlogEntity> list = GetBlogList(pageSize);// 下载最后一页
		for (int i = 0, len = list.size(); i < len; i++) {
			listBlogs.addAll(list);
			if (list.get(i).GetBlogId() == lastNum) {
				break;
			}
		}
		// 内容
		for (int i = 0, len = listBlogs.size(); i < len; i++) {
			String content = GetBlogContentByIdWithNet(listBlogs.get(i)
					.GetBlogId());
			listBlogs.get(i).SetBlogContent(content);

			listBlogs.get(i).SetIsFullText(true);// 更新全文标志
		}

		return listBlogs;
	}

	/**
	 * 根据页码返回Blog对象集合
	 * 
	 * @return pageIndex:页码，从1开始
	 */
	public static ArrayList<BlogEntity> GetBlogList(int pageIndex) {
		String url = AppConfig.URL_GET_BLOG_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex));// 数据地址
		String dataString = HttpUtil.geturl(url, null);

		ArrayList<BlogEntity> list = ParseString(dataString);

		return list;
	}

	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static ArrayList<BlogEntity> ParseString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		ArrayList<BlogEntity> listBlog = new ArrayList<BlogEntity>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			BlogListXmlParser handler = new BlogListXmlParser(listBlog);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			listBlog = handler.GetBlogList();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listBlog;
	}

	/**
	 * 根据编号获取博客内容
	 * 
	 * @param blogId
	 * @return
	 */
	public static String GetBlogContentByIdWithNet(int blogId) {
		String blogContent = "";
		String url = "";// Config.URL_GET_BLOG_DETAIL.replace("{0}",String.valueOf(blogId));//
						// 网址
		String xml = HttpUtil.geturl(url, null);
		if (xml == "") {
			return "";
		}
		blogContent = ParseBlogString(xml);

		return blogContent;
	}

	/**
	 * 根据编号获取博客内容(先取本地，再取网络)
	 * 
	 * @param blogId
	 * @return
	 */
	public static String GetBlogById(int blogId, Context context) {
		String blogContent = "";
		// 优先考虑本地数据
		BlogDalHelper helper = new BlogDalHelper(context);
		BlogEntity entity = helper.GetBlogEntity(blogId);
		if (null == entity || entity.GetBlogContent().equals("")) {
			blogContent = GetBlogContentByIdWithNet(blogId);
		} else {
			blogContent = entity.GetBlogContent();
		}
		return blogContent;
	}

	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static String ParseBlogString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		String blogContent = "";
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			BlogXmlParser handler = new BlogXmlParser(blogContent);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			blogContent = handler.GetBlogContent();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blogContent;
	}
}
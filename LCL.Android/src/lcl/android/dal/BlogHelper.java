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
 * Blog������
 * 
 * @author walkingp
 * 
 */
public class BlogHelper extends DefaultHandler {
	/**
	 * ��������
	 * 
	 * @param top
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<BlogEntity> DownloadOfflineBlogList(int top) {
		int pageSize = top / AppConfig.BLOG_PAGE_SIZE;
		int lastNum = pageSize % AppConfig.BLOG_PAGE_SIZE;

		List<BlogEntity> listBlogs = null;
		// ����ǰ��ҳ
		for (int i = 0; i < pageSize; i++) {
			List<BlogEntity> list = GetBlogList(i);

			listBlogs.addAll(list);
		}
		// ����ʣ������
		List<BlogEntity> list = GetBlogList(pageSize);// �������һҳ
		for (int i = 0, len = list.size(); i < len; i++) {
			listBlogs.addAll(list);
			if (list.get(i).GetBlogId() == lastNum) {
				break;
			}
		}
		// ����
		for (int i = 0, len = listBlogs.size(); i < len; i++) {
			String content = GetBlogContentByIdWithNet(listBlogs.get(i)
					.GetBlogId());
			listBlogs.get(i).SetBlogContent(content);

			listBlogs.get(i).SetIsFullText(true);// ����ȫ�ı�־
		}

		return listBlogs;
	}

	/**
	 * ����ҳ�뷵��Blog���󼯺�
	 * 
	 * @return pageIndex:ҳ�룬��1��ʼ
	 */
	public static ArrayList<BlogEntity> GetBlogList(int pageIndex) {
		String url = AppConfig.URL_GET_BLOG_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex));// ���ݵ�ַ
		String dataString = HttpUtil.geturl(url, null);

		ArrayList<BlogEntity> list = ParseString(dataString);

		return list;
	}

	/**
	 * ���ַ���ת��ΪBlog����
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
	 * ���ݱ�Ż�ȡ��������
	 * 
	 * @param blogId
	 * @return
	 */
	public static String GetBlogContentByIdWithNet(int blogId) {
		String blogContent = "";
		String url = "";// Config.URL_GET_BLOG_DETAIL.replace("{0}",String.valueOf(blogId));//
						// ��ַ
		String xml = HttpUtil.geturl(url, null);
		if (xml == "") {
			return "";
		}
		blogContent = ParseBlogString(xml);

		return blogContent;
	}

	/**
	 * ���ݱ�Ż�ȡ��������(��ȡ���أ���ȡ����)
	 * 
	 * @param blogId
	 * @return
	 */
	public static String GetBlogById(int blogId, Context context) {
		String blogContent = "";
		// ���ȿ��Ǳ�������
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
	 * ���ַ���ת��ΪBlog����
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
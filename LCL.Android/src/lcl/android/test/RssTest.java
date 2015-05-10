package lcl.android.test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lcl.android.dal.BlogHelper;
import lcl.android.entity.BlogEntity;
import android.test.AndroidTestCase;

public class RssTest extends AndroidTestCase {

	public void TestContent() {
		Pattern p = Pattern.compile("<div.*id='test'.*</div>");
		String str = "<html><body>aa<div id='test'>bb</div></body></html>";
		Matcher m = p.matcher(str);
		System.out.println(m);
	}

	public void TestBlogList() {
		List<BlogEntity> list = BlogHelper.DownloadOfflineBlogList(10);
		if (list.size() > 0)
			System.out.println("TestBlogList ³É¹¦");
		else
			System.out.println("TestBlogList Ê§°Ü");
	}
}

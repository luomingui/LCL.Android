package lcl.android.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: HTML相关的正则表达式工具类
 * </p>
 * <p>
 * Description: 包括过滤HTML标记，转换HTML标记，替换特定HTML标记
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * @version 1.0
 * @createtime 2006-10-16
 */
public class HtmlRegexpUtil {
	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

	private static String regxpForDiv = "(?is)<div\\b(?:(?!id=).)*id=(['\"\"]?)cnblogs_post_body\\1[^>]*>((?><div[^>]*>(?<o>)|</div>(?<-o>)|(?:(?!</?div\\b).)*)*(?(o)(?!)))</div>";

	public HtmlRegexpUtil() {

	}

	/**
	 * 基本功能：替换标记以正常显示
	 * 
	 * @param input
	 * @return String
	 */
	public static String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}

	/**
	 * 基本功能：判断标记是否存在
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if (input != null) {
			char c;
			for (int i = 0; i < input.length(); i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 基本功能：过滤指定标签
	 * 
	 * @param str
	 * @param tag
	 *            指定标签
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 提取"<title>XXXX</title>"中的文字XXXX
	 * 
	 * @param html
	 *            要解析的html文档内容
	 * @return 解析结果，可以多次匹配，每次匹配的结果按文档中出现的先后顺序添加进结果List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getContext(String html) {
		List resultList = new ArrayList();
		Pattern p = Pattern.compile("<title>([^</title>]*)");// 匹配<title>开头，</title>结尾的文档
		Matcher m = p.matcher(html);// 开始编译
		while (m.find()) {
			resultList.add(m.group(1));// 获取被匹配的部分
		}
		return resultList;
	}

	/**
	 * 基本功能：读取div id= cnblogs_post_body 里的内容
	 * 
	 * @return String
	 */
	public static String readHtmlDiv(String html) {
		try {
			regxpForDiv = "<div.*id=\"cnblogs_post_body\".*</div>";
			Pattern pattern = Pattern.compile(regxpForDiv);
			Matcher matcher = pattern.matcher(html);
			while (matcher.find()) {
				return matcher.group(0);
			}
			// 去除标签
			String result = matcher.replaceAll("");
			return result;

		} catch (Exception e) {
			LogUtils.Error("HtmlRegexpUtil readHtmlDiv error ：", e);
			return fiterHtmlTag(html, "script");
		}
	}

	/**
	 * 基本功能：替换指定的标签
	 * 
	 * @param str
	 * @param beforeTag
	 *            要替换的标签
	 * @param tagAttrib
	 *            要替换的标签属性值
	 * @param startTag
	 *            新标签开始标记
	 * @param endTag
	 *            新标签结束标记
	 * @return String
	 * @如：替换img标签的src属性值为[img]属性值[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,
			String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag
						+ matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}
}
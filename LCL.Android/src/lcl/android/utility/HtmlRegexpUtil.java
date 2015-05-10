package lcl.android.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: HTML��ص�������ʽ������
 * </p>
 * <p>
 * Description: ��������HTML��ǣ�ת��HTML��ǣ��滻�ض�HTML���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * @version 1.0
 * @createtime 2006-10-16
 */
public class HtmlRegexpUtil {
	private final static String regxpForHtml = "<([^>]*)>"; // ����������<��ͷ��>��β�ı�ǩ

	private static String regxpForDiv = "(?is)<div\\b(?:(?!id=).)*id=(['\"\"]?)cnblogs_post_body\\1[^>]*>((?><div[^>]*>(?<o>)|</div>(?<-o>)|(?:(?!</?div\\b).)*)*(?(o)(?!)))</div>";

	public HtmlRegexpUtil() {

	}

	/**
	 * �������ܣ��滻�����������ʾ
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
	 * �������ܣ��жϱ���Ƿ����
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
	 * �������ܣ�����������"<"��ͷ��">"��β�ı�ǩ
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
	 * �������ܣ�����ָ����ǩ
	 * 
	 * @param str
	 * @param tag
	 *            ָ����ǩ
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
	 * ��ȡ"<title>XXXX</title>"�е�����XXXX
	 * 
	 * @param html
	 *            Ҫ������html�ĵ�����
	 * @return ������������Զ��ƥ�䣬ÿ��ƥ��Ľ�����ĵ��г��ֵ��Ⱥ�˳����ӽ����List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getContext(String html) {
		List resultList = new ArrayList();
		Pattern p = Pattern.compile("<title>([^</title>]*)");// ƥ��<title>��ͷ��</title>��β���ĵ�
		Matcher m = p.matcher(html);// ��ʼ����
		while (m.find()) {
			resultList.add(m.group(1));// ��ȡ��ƥ��Ĳ���
		}
		return resultList;
	}

	/**
	 * �������ܣ���ȡdiv id= cnblogs_post_body �������
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
			// ȥ����ǩ
			String result = matcher.replaceAll("");
			return result;

		} catch (Exception e) {
			LogUtils.Error("HtmlRegexpUtil readHtmlDiv error ��", e);
			return fiterHtmlTag(html, "script");
		}
	}

	/**
	 * �������ܣ��滻ָ���ı�ǩ
	 * 
	 * @param str
	 * @param beforeTag
	 *            Ҫ�滻�ı�ǩ
	 * @param tagAttrib
	 *            Ҫ�滻�ı�ǩ����ֵ
	 * @param startTag
	 *            �±�ǩ��ʼ���
	 * @param endTag
	 *            �±�ǩ�������
	 * @return String
	 * @�磺�滻img��ǩ��src����ֵΪ[img]����ֵ[/img]
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
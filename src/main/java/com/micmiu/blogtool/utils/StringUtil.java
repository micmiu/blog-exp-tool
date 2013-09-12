package com.micmiu.blogtool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class StringUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String str = "http://www.micmiu.com/page/20/";

			System.out.println(StringUtil.parsePageUrl(str));

			System.out.println(StringUtil.parsePageMax(str));

			System.out
					.println(StringUtil
							.parseCategory("http://www.micmiu.com/category/lang/java/"));
			System.out.println(StringUtil.parseCategory("category/lang/java/"));

			System.out
					.println(StringUtil
							.parseCategory("http://www.micmiu.com/lang/java/java-check-chinese/"));
			System.out
					.println(StringUtil
							.parseCategory("http://www.micmiu.com/lang/java-check-chinese/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parsePageUrl(String url) {
		return url.replaceAll("\\d+", "{0}");
	}

	public static int parsePageMax(String url) {
		String reg = "\\d+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(url);
		int max = 0;
		if (m.find()) {
			max = Integer.parseInt(m.group());
		}

		return max;
	}

	public static String parseCategory(String URL) {
		int index = URL.indexOf("category/");
		if (index >= 0) {
			return parseCategoryFromTagURL(URL);
		} else {
			return parseCategoryFromURL(URL);
		}
	}

	// http://www.micmiu.com/category/lang/java/
	public static String parseCategoryFromTagURL(String tagURL) {
		int index = tagURL.indexOf("category/");
		if (index >= 0) {
			return tagURL.split("category/")[1];
		} else {
			return tagURL;
		}
	}

	// http://www.micmiu.com/lang/java/java-check-chinese/
	public static String parseCategoryFromURL(String blogURL) {
		int index = blogURL.indexOf("www.micmiu.com/");
		if (index >= 0) {

			String[] arr = blogURL.split("www.micmiu.com/")[1].split("/");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length - 1; i++) {
				sb.append(arr[i] + "/");
			}
			return sb.toString();
		} else {
			return blogURL;
		}
	}
}

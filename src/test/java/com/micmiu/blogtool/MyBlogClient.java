package com.micmiu.blogtool;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.code.http4j.Client;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.BasicClient;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-9-16 上午9:15:27 
 * @version 1.0
 */
public class MyBlogClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pageExpr = "http://www.micmiu.com/page/{0}/";
		int start = 1, end = 1;
		List<String> pageList = getPageList(pageExpr, start, end);
		for (String page : pageList) {
			System.out.println(" ---->:" + page);
			List<String> blogList = getBlogList(page);
		}
	}

	public static List<String> getPageList(String pageExpr, int start, int end) {
		List<String> pageList = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			pageList.add(MessageFormat.format(pageExpr, i));
		}

		return pageList;

	}

	public static List<String> getBlogList(String pageUrl) {
		List<String> urlList = new ArrayList<String>();
		try {
			Client client = new BasicClient();
			Response response = client.get(pageUrl);
			if (null != response.getRedirectLocation()) {
				response = client.get(response.getRedirectLocation());
			}
			response.output(System.out);

			client.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlList;
	}
}

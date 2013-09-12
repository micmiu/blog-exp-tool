package com.micmiu.blogtool.html;

import java.util.List;
import java.util.Map;

import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface IHtmlHandler {
	/**
	 * 根据首页规则提取所有的page URL
	 */
	List<String> fetchPageList(String homeURL) throws Exception;

	/**
	 * 提取page 页面中blog的 标题和 URL
	 */
	Map<String, String> fetchBlogInfoMap(String pageURL) throws Exception;

	/**
	 * 提取page页面中blog的信息
	 */
	List<BlogInfo> fetchBlogVoList(String pageURL) throws Exception;

	/**
	 * 提取page 页面中blog的 信息转化为category结构
	 */
	void fetchBlogInfo(Map<String, Category> categoryMap, String pageURL)
			throws Exception;

	/**
	 * 提取page页面中blog的信息
	 */
	BlogInfo fetchBlogVo(String blogURL) throws Exception;

	/**
	 * return [title,category,post date,content]
	 */
	String[] extractBlogInfo(String blogURL) throws Exception;

	/**
	 * return [title,category,post date,content]
	 */
	String[] extractBlogInfo(String blogURL, boolean contentFlag)
			throws Exception;

}

package com.micmiu.blogtool.export;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface IExpHandler {

	/**
	 * 根据网站首页完整的导出
	 * 
	 * @param fileName
	 * @param indexURL
	 */
	void expBlogByIndexURL(String fileName, String indexURL);

	/**
	 * 根据pageURL中的每个blog单独导出
	 * 
	 * @param pageURL
	 */
	void expBlogByPageURL(String pageURL);

	/**
	 * 根据blogURL导出
	 * 
	 * @param fileName
	 * @param blogURL
	 */
	void expBlogByBlogURL(String blogURL);

	/**
	 * 根据文件列表反序列化后导出
	 * 
	 * @param fileName
	 * @param filePath
	 */
	void expBlogByFileList(String fileName, String filePath);

	/**
	 * 根据单个文件反序列化后导出
	 * 
	 * @param fileName
	 * @param objFile
	 */
	void expBlogByFile(String fileName, String objFile);
}

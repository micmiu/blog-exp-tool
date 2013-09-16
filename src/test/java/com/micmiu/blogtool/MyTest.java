package com.micmiu.blogtool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.micmiu.blogtool.html.IHtmlHandler;
import com.micmiu.blogtool.html.JsoupHtmlHandler;
import com.micmiu.blogtool.utils.FileUtil;
import com.micmiu.blogtool.utils.MyContants;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;


/**
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-9-16 上午9:15:21 
 * @version 1.0
 */
public class MyTest {

	public static void main(String[] args) throws Exception {

		Category cate = new Category("test");
		List<BlogInfo> volist = new ArrayList<BlogInfo>();
		BlogInfo blog = new BlogInfo();
		blog.setPostDate("发表日期: 2012 年 4 月 26 日");
		blog.setTitle("发表日期: 2012 年 4 月 26 日");
		volist.add(blog);
		blog = new BlogInfo();
		blog.setPostDate("发表日期: 2012 年 4 月 4 日");
		blog.setTitle("发表日期: 2012 年 4 月 4 日");
		volist.add(blog);
		blog = new BlogInfo();
		blog.setPostDate("发表日期: 2012 年 2 月 8 日");
		blog.setTitle("发表日期: 2012 年 2 月 8 日");
		volist.add(blog);
		cate.setBlogList(volist);

		Collections.sort(cate.getBlogList());
		for (BlogInfo vo : cate.getBlogList()) {
			System.out.println(vo.getTitle());
		}

		// App.testPDF("http://www.micmiu.com/lang/java/java-check-chinese/");
		// MyTest.testFetchCategory();
		System.out.println("-------------- end.");

	}

	public static void testFetchCategory() throws Exception {
		IHtmlHandler hander = new JsoupHtmlHandler();
		String homeURL = "http://www.micmiu.com";
		List<String> list = hander.fetchPageList(homeURL);
		Map<String, Category> categoryMap = MyContants.INIT_CATE_MAP;
		for (String page : list) {
			System.out.println("===================");
			System.out.println("page >>:" + page);
			hander.fetchBlogInfo(categoryMap, page);
		}
		FileUtil.object2File(categoryMap, "d:/test/itext/blogcontentxx.obj");

		// Map<String, Category> categoryMap = (Map<String, Category>) BlogUtil
		// .file2Object("d:/test/itext/blogcontent.obj");
		// for (Map.Entry<String, Category> entry : categoryMap.entrySet()) {
		// printCateMap(entry.getValue(), "");
		// }
	}

	private static void printCateMap(Category category, String prefix) {
		System.out.println(prefix + "==>: " + category.getName());
		for (Category sub : category.getSubCateMap().values()) {
			printCateMap(sub, prefix + "    ");
		}
		for (BlogInfo vo : category.getBlogList()) {
			System.out.println(prefix + "    ==>: " + vo.getTitle() + " > "
					+ vo.getBlogURL());
			System.out.println(prefix + "         : " + vo.getContent());

		}

	}

	public static void testPDF(String blogURL) throws Exception {
		IHtmlHandler hander = new JsoupHtmlHandler();
		String rootPath = "d:/test/itext/";
		String[] infoArr = hander.extractBlogInfo(blogURL);
		System.out.println(" title    >>: " + infoArr[0]);
		System.out.println(" categroy >>: " + infoArr[1]);
		String pdfFile = rootPath + FileUtil.parseFileName(infoArr[0]) + ".pdf";
		HTML2PDFByXMLWorker pdfHandler = new HTML2PDFByXMLWorker();
		pdfHandler.buildHTML2Element(pdfHandler.parse2Stream(infoArr[3]),
				pdfFile);

	}

	public static void testURL() throws Exception {
		IHtmlHandler hander = new JsoupHtmlHandler();
		String homeURL = "http://www.micmiu.com";
		List<String> list = hander.fetchPageList(homeURL);
		for (String page : list) {
			System.out.println("===================");
			System.out.println("page >>:" + page);
			Map<String, String> infoMap = hander.fetchBlogInfoMap(page);
			for (Map.Entry<String, String> entry : infoMap.entrySet()) {
				System.out.println("    " + entry.getKey() + " --> "
						+ entry.getValue());
			}
		}
	}
}

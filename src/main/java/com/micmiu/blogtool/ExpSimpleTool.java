package com.micmiu.blogtool;

import com.micmiu.blogtool.export.ExpPDFHandler;
import com.micmiu.blogtool.html.JsoupHtmlHandler;
import com.micmiu.blogtool.pdf.ItextPDFHandler;
import com.micmiu.blogtool.utils.FileUtil;
import com.micmiu.blogtool.utils.MyContants;

public class ExpSimpleTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExpPDFHandler expHandler = new ExpPDFHandler(new ItextPDFHandler(),
				new JsoupHtmlHandler());

		String pdfFile = MyContants.EXP_ROOT_PATH + "micmiu.com_"
				+ FileUtil.getDateSuffix() + ".pdf";
//		expHandler.expBlogByIndexURL(pdfFile, MyContants.MY_BLOG_URL);

		expHandler.expBlogByFileList(pdfFile, MyContants.EXP_OBJ_PATH);
//
//		expHandler.expBlogByFile(pdfFile, MyContants.EXP_ROOT_PATH
//				+ "micmiu_2012-12-12.obj");

//		expHandler.expBlogByPageURL(MyContants.MY_BLOG_URL);

//		String blogURL = "http://www.micmiu.com/j2ee/jta/jta-spring-atomikos/";
//		expHandler.expBlogByBlogURL(blogURL);
		System.out.println("--------------E N D");

	}
}

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
		//根据博客首页导出所有的文章
//		expHandler.expBlogByIndexURL(pdfFile, MyContants.MY_BLOG_URL);

		//根据pdfobjcet生产PDF文件
		expHandler.expBlogByFileList(pdfFile, MyContants.EXP_OBJ_PATH);

		//根据指定obj生产pdf
//		expHandler.expBlogByFile(pdfFile, MyContants.EXP_ROOT_PATH+ "micmiu_2012-12-12.obj");

		//根据url导出当前的文章
//		expHandler.expBlogByPageURL(MyContants.MY_BLOG_URL);

//		String blogURL = "http://www.micmiu.com/exception/deprecated-restricted-api/";
//		expHandler.expBlogByBlogURL(blogURL);
		System.out.println("--------------E N D");

	}
}

package com.micmiu.blogtool.export;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.micmiu.blogtool.html.IHtmlHandler;
import com.micmiu.blogtool.pdf.IPDFHandler;
import com.micmiu.blogtool.utils.BlogUtil;
import com.micmiu.blogtool.utils.FileUtil;
import com.micmiu.blogtool.utils.MyContants;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class ExpPDFHandler implements IExpHandler {

	private static final Logger logger = Logger.getLogger(ExpPDFHandler.class);

	private IPDFHandler pdfHandler;

	private IHtmlHandler htmlHander;

	public ExpPDFHandler(IPDFHandler pdfHandler, IHtmlHandler htmlHander) {
		this.pdfHandler = pdfHandler;
		this.htmlHander = htmlHander;
	}

	public void expBlogByIndexURL(String fileName, String indexURL) {
		try {
			Document document = BlogUtil.createPDFDoc(fileName);
			Map<String, Category> cateMap = this.fetchByIndexURL(indexURL);
			pdfHandler.buildBlog2PDF(document, cateMap.values());
			document.close();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void expBlogByPageURL(String pageURL) {
		try {

			List<BlogInfo> list = htmlHander.fetchBlogVoList(pageURL);
			if (!MyContants.EXP_PDF) {
				return;
			}
			String pdfFile = null;
			String dstFile = null;
			for (BlogInfo vo : list) {
				pdfFile = MyContants.EXP_PDF_PATH
						+ FileUtil.parseFileName(vo.getCategoryTag()
								+ vo.getTitle()) + ".pdf";
				System.out.println(">>>: " + pdfFile);
				Document document = BlogUtil.createPDFDoc(pdfFile);
				pdfHandler.buildBlog2PDF(document, vo);
				document.close();
				if (MyContants.PDF_ADD_HEADER) {
					dstFile = MyContants.EXP_SHARE_PATH
							+ FileUtil.parseFileName(vo.getTitle()) + ".pdf";
					BlogUtil.addHeader(pdfFile, dstFile);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void expBlogByBlogURL(String blogURL) {

		try {
			BlogInfo vo = htmlHander.fetchBlogVo(blogURL);

			String pdfFile = MyContants.EXP_PDF_PATH
					+ FileUtil.parseFileName(vo.getCategoryTag()
							+ vo.getTitle()) + ".pdf";
			System.out.println(">>>: " + pdfFile);
			Document document = BlogUtil.createPDFDoc(pdfFile);
			pdfHandler.buildBlog2PDF(document, vo);
			document.close();
			if (MyContants.PDF_ADD_HEADER) {
				String dstFile = MyContants.EXP_SHARE_PATH
						+ FileUtil.parseFileName(vo.getTitle()) + ".pdf";
				BlogUtil.addHeader(pdfFile, dstFile);
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void expBlogByFileList(String fileName, String filePath) {
		try {
			Document document = BlogUtil.createPDFDoc(fileName);
			if (null == filePath) {
				filePath = MyContants.EXP_OBJ_PATH;
			}
			Map<String, Category> cateMap = this.fetchByFileList(filePath);
			pdfHandler.buildBlog2PDF(document, cateMap.values());

			document.close();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void expBlogByFile(String fileName, String objFile) {
		try {
			Document document = BlogUtil.createPDFDoc(objFile);
			@SuppressWarnings("unchecked")
			Map<String, Category> cateMap = (Map<String, Category>) FileUtil
					.file2Object(objFile);
			pdfHandler.buildBlog2PDF(document, cateMap.values());

			document.close();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/**
	 * 根据首页URL解析所有的blog
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Category> fetchByIndexURL(String indexURL)
			throws Exception {
		String homeURL = indexURL;
		if (null == homeURL) {
			homeURL = MyContants.MY_BLOG_URL;
		}
		List<String> list = htmlHander.fetchPageList(homeURL);
		Map<String, Category> categoryMap = MyContants.INIT_CATE_MAP;
		for (String page : list) {
			System.out.println("===================");
			System.out.println("page >>:" + page);
			htmlHander.fetchBlogInfo(categoryMap, page);
		}
		if (MyContants.EXP_OBJ) {
			FileUtil.object2File(categoryMap, MyContants.EXP_ROOT_PATH
					+ "micmiu_" + FileUtil.getDateSuffix() + ".obj");
		}
		return categoryMap;
	}

	/**
	 * 根据导出的文件列表解析所有的blog
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Category> fetchByFileList(String filePath)
			throws Exception {
		Map<String, Category> categoryMap = MyContants.INIT_CATE_MAP;
		File[] files = new File(filePath).listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isFile() && pathname.getName().endsWith(".obj")) {
					return true;
				}
				return false;
			}
		});

		for (File file : files) {
			try {
				BlogInfo vo = (BlogInfo) FileUtil.file2Object(file);
				System.out.println(">>>: " + file.getName() + " | "
						+ vo.getTitle());
				BlogUtil.convertCategoryMap(categoryMap, vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return categoryMap;

	}

}

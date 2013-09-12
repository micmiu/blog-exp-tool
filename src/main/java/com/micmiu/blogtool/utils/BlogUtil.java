package com.micmiu.blogtool.utils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.jsoup.Jsoup;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class BlogUtil {

	public static Font PDF_FONT_CH = new Font(MyContants.PDF_CN_BASE_FONT, 12,
			Font.BOLD, new BaseColor(255, 0, 0));

	public static Font PDF_FONT_SEC = new Font(MyContants.PDF_CN_BASE_FONT, 12,
			Font.NORMAL, new BaseColor(0, 0, 255));

	public static Font PDF_FONT_TITLE = new Font(MyContants.PDF_CN_BASE_FONT,
			12, Font.NORMAL, new BaseColor(0, 204, 255));

	public static Font PDF_FONT_CN = new Font(MyContants.PDF_CN_BASE_FONT, 12,
			Font.NORMAL);

	public static org.jsoup.nodes.Document getHTMLContent(String URL) {
		int i = 0;
		while (i < MyContants.MAX_RETRY_TIMES) {
			try {
				return Jsoup.connect(URL).get();
			} catch (Exception e) {
				i++;
				e.printStackTrace();
				try {
					Thread.sleep(MyContants.SLEEP_BLOG_URL);
				} catch (Exception e1) {
				}
			}
		}
		return null;
	}

	public static Document createPDFDoc(String pdfFile) {
		Document document = new Document(PageSize.A4);
		try {

			// document.setMargins(0, 0, 0, 0);
			FileOutputStream fos = new FileOutputStream(pdfFile);
			PdfWriter pdfwriter = PdfWriter.getInstance(document, fos);

			// pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
			pdfwriter.setViewerPreferences(PdfWriter.PageModeUseOutlines);
			document.open();

			document.addAuthor(MyContants.MY_NAME);
			document.addCreator(MyContants.MY_NAME);
			document.addTitle(MyContants.MY_BLOG_URL);
			document.addSubject("Thanks for your support.");
			document.addCreationDate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;

	}

	public static void addHeader(String srcPdf, String dstPdf) {
		try {
			PdfReader reader = new PdfReader(srcPdf);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					dstPdf));
			int n = reader.getNumberOfPages();
			for (int i = 1; i <= n; i++) {
				getHeaderTable(i, n).writeSelectedRows(0, -1, 30, 825,
						stamper.getOverContent(i));
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:" + srcPdf + " | add header excetion.");
		}

	}

	/**
	 * Create a header table with page X of Y
	 * 
	 * @param x
	 *            the page number
	 * @param y
	 *            the total number of pages
	 * @return a table that can be used as header
	 */
	public static PdfPTable getHeaderTable(int x, int y) {
		Font fontCN = null;
		try {
			BaseFont bfCN = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			fontCN = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLACK);
		} catch (Exception e) {
			fontCN = new Font();
		}
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(530);
		table.setLockedWidth(true);
		table.getDefaultCell().setFixedHeight(20);
		table.getDefaultCell().setBorder(Rectangle.OUT_TOP);
		table.addCell(new Paragraph(new Chunk("http://www.micmiu.com", fontCN)
				.setAnchor("http://www.micmiu.com")));
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(new Paragraph(String.format("第  %d 页, 共  %d 页", x, y),
				fontCN));
		return table;
	}

	public static InputStream parse2Stream(String content) {
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(
					content.getBytes("utf-8"));
			return stream;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 
	 * @param cate
	 * @param section
	 */
	public static void recursionCategory(Category cate, Section section) {
		LineSeparator line = new LineSeparator(1, 100, new BaseColor(204, 204,
				204), Element.ALIGN_CENTER, -2);
		Paragraph p_line = new Paragraph(" ");
		p_line.add(line);

		for (Category cateSub : cate.getSubCateMap().values()) {
			Section subsection = section.addSection(new Paragraph(cateSub
					.getName(), BlogUtil.PDF_FONT_SEC));
			subsection.setBookmarkTitle(cateSub.getName());
			subsection.setIndentation(10);
			subsection.setIndentationLeft(10);
			subsection.setBookmarkOpen(false);
			subsection
					.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			recursionCategory(cateSub, subsection);
		}
		for (BlogInfo vo : cate.getBlogList()) {
			Section subsection = section.addSection(new Paragraph(new Chunk(vo
					.getTitle(), BlogUtil.PDF_FONT_TITLE).setUnderline(0.2f,
					-2f).setAnchor(vo.getBlogURL())));
			subsection.setBookmarkTitle(vo.getTitle());
			subsection.setIndentation(10);
			subsection.setIndentationLeft(10);
			subsection.setBookmarkOpen(false);
			subsection
					.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			subsection.add(p_line);
			if (null != vo.getContent() && !"".equals(vo.getContent())) {

			}
		}
	}

	public static void convertCategoryMap(Map<String, Category> categoryMap,
			BlogInfo vo) {
		convertCategoryMap(categoryMap, vo, false);
	}

	/**
	 * flag -> 是否创建未分类的标签
	 * 
	 * @param categoryMap
	 * @param vo
	 * @param flag
	 */
	public static void convertCategoryMap(Map<String, Category> categoryMap,
			BlogInfo vo, Boolean flag) {
		String cateStr = vo.getCategoryTag();
		if (null == cateStr || "".equals(cateStr)) {
			cateStr = "未分类";
		}
		if (cateStr.endsWith("/")) {
			cateStr = cateStr.substring(0, cateStr.length() - 1);
		}
		String[] arr = cateStr.split("/");
		String mainCate = cateStr, subCate = null;
		if (arr.length > 1) {
			mainCate = arr[0];
			subCate = arr[1];
		}
		if (null != MyContants.CATE_NAME_MAP.get(mainCate)) {
			mainCate = MyContants.CATE_NAME_MAP.get(mainCate);
		}
		if (null != MyContants.CATE_NAME_MAP.get(subCate)) {
			subCate = MyContants.CATE_NAME_MAP.get(subCate);
		} else if (flag && (null == subCate || "".equals(subCate))) {
			subCate = "未分类";
		}
		if (null == categoryMap.get(mainCate)) {
			categoryMap.put(mainCate, new Category(mainCate));
		}
		Category category = categoryMap.get(mainCate);
		if (null == subCate || "".equals(subCate)) {
			category.getBlogList().add(vo);
		} else {
			if (null == category.getSubCateMap().get(subCate)) {
				category.getSubCateMap().put(subCate, new Category(subCate));
			}
			category.getSubCateMap().get(subCate).getBlogList().add(vo);
		}

	}

}

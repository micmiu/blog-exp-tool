package com.micmiu.blogtool;

import java.io.FileOutputStream;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.micmiu.blogtool.html.IHtmlHandler;
import com.micmiu.blogtool.html.JsoupHtmlHandler;
import com.micmiu.blogtool.utils.FileUtil;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

public class BlogCategory {

	public static void main(String[] args) throws Exception {
		IHtmlHandler hander = new JsoupHtmlHandler();
		// String homeURL = "http://www.micmiu.com";
		// List<String> list = hander.fetchPageList(homeURL);
		// Map<String, Category> categoryMap = BlogUtil.initCategoryNameMap();
		// for (String page : list) {
		// System.out.println("===================");
		// System.out.println("page >>:" + page);
		// hander.fetchBlogInfo(categoryMap, page);
		// }
		// BlogUtil.object2File(categoryMap, "d:/test/itext/categoryMap.obj");

		Map<String, Category> categoryMap = (Map<String, Category>) FileUtil
				.file2Object("d:/test/itext/categoryMap.obj");
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
		}

	}

	public static void buildPDF(String fileName,
			Map<String, Category> categoryMap) throws Exception {
		BaseFont bfCN = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
				false);
		Font fontCN = new Font(bfCN, 12, Font.NORMAL,
				new BaseColor(0, 204, 255));

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(fileName));

		document.open();
		Chapter chapter = null;
		int chNum = 1;
		for (Map.Entry<String, Category> entry : categoryMap.entrySet()) {
			catgory2PDF(entry.getValue(), fontCN, chNum++);
		}

		Section section = chapter.addSection(new Paragraph("子类：java", fontCN));
		// section.setNumberDepth(2);
		section.setBookmarkTitle("子类：java");
		section.setIndentation(10);
		section.setIndentationLeft(10);
		section.setBookmarkOpen(false);
		section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

		section = section.addSection(new Paragraph(new Chunk("Java 完美判断中文字符",
				fontCN).setUnderline(0.2f, -2f).setAnchor(
				"http://www.micmiu.com/lang/java/java-check-chinese/")));
		// section.setNumberDepth(2);
		section.setBookmarkTitle("Java 完美判断中文字符");
		// section.setIndentation(30);
		// section.setIndentationLeft(10);
		section.setBookmarkOpen(false);
		section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
		section.add(new Paragraph("内容如下：", fontCN));
		LineSeparator line = new LineSeparator(1, 100, new BaseColor(204, 204,
				204), Element.ALIGN_CENTER, -2);

		Paragraph p_line = new Paragraph(" ");
		p_line.add(line);
		chapter.add(p_line);

		section = chapter.addSection(new Paragraph("子类：Ruby", fontCN));
		// section.setNumberDepth(1);
		section.setBookmarkTitle("子类：Ruby");
		section.setIndentation(10);
		section.setIndentationLeft(10);
		section.setBookmarkOpen(false);
		section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
		section.add(new Paragraph("内容如下：", fontCN));
		section.add(p_line);

		document.add(chapter);
		// step 5
		document.close();
	}

	private static void catgory2PDF(Category category, Font fontCN, int chNum) {
		Chapter chapter = new Chapter(
				new Paragraph(category.getName(), fontCN), chNum);
		for (Map.Entry<String, Category> subEntry : category.getSubCateMap()
				.entrySet()) {
			Section section = chapter.addSection(new Paragraph(subEntry
					.getValue().getName(), fontCN));
			section.setBookmarkTitle(subEntry.getValue().getName());
			section.setIndentation(10);
			section.setIndentationLeft(10);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			for (BlogInfo blog : subEntry.getValue().getBlogList()) {
				section = section.addSection(new Paragraph(new Chunk(blog
						.getTitle(), fontCN).setUnderline(0.2f, -2f).setAnchor(
						blog.getBlogURL())));
				section.setBookmarkTitle(blog.getTitle());
				section.setBookmarkOpen(false);
				section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				section.add(new Paragraph("内容如下：", fontCN));
				LineSeparator line = new LineSeparator(1, 100, new BaseColor(
						204, 204, 204), Element.ALIGN_CENTER, -2);
				Paragraph p_line = new Paragraph(" ");
				p_line.add(line);
				section.add(p_line);
			}

		}

	}
}

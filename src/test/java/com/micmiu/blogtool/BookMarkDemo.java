package com.micmiu.blogtool;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.micmiu.blogtool.utils.BlogUtil;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

public class BookMarkDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String fileName = "d:/test/itext/bookmark-2.pdf";
		// BookMarkDemo.testBookMarks(fileName);

		BookMarkDemo.testBookMarks(fileName, BookMarkDemo.initTestData());

		// 7 154 255
	}

	public static List<Category> initTestData() {
		List<Category> list = new ArrayList<Category>();

		Category cate = new Category("J2EE");

		Map<String, Category> subCates = new HashMap<String, Category>();

		List<BlogInfo> volist = new ArrayList<BlogInfo>();
		BlogInfo vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("Hibernate 笔记1");
		volist.add(vo);
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("Hibernate 笔记2");
		volist.add(vo);
		Category cate1 = new Category("Hibernate");
		cate1.setBlogList(volist);

		subCates.put(cate1.getName(), cate1);

		Category cate2 = new Category("Struts");
		volist = new ArrayList<BlogInfo>();
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("Struts 笔记1");
		volist.add(vo);
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("Struts 笔记2");
		volist.add(vo);
		cate2.setBlogList(volist);

		subCates.put(cate2.getName(), cate2);
		cate.setSubCategorMap(subCates);

		list.add(cate);

		cate = new Category("架构");
		subCates = new HashMap<String, Category>();
		cate1 = new Category("集群");
		volist = new ArrayList<BlogInfo>();
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("JGroup");
		volist.add(vo);
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("Keepalived");
		volist.add(vo);
		cate1.setBlogList(volist);

		subCates.put(cate1.getName(), cate1);

		cate2 = new Category("缓存");

		volist = new ArrayList<BlogInfo>();
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("ehcache");
		volist.add(vo);
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("OScache");
		volist.add(vo);
		cate2.setBlogList(volist);

		Category cate2Sub = new Category("子类");
		volist = new ArrayList<BlogInfo>();
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("blog1");
		volist.add(vo);
		vo = new BlogInfo();
		vo.setBlogURL("http://www.micmiu.com/");
		vo.setTitle("blog2");
		volist.add(vo);
		cate2Sub.setBlogList(volist);
		cate2.getSubCateMap().put(cate2Sub.getName(), cate2Sub);

		subCates.put(cate2.getName(), cate2);

		cate.setSubCategorMap(subCates);

		list.add(cate);
		return list;
	}

	public static void testBookMarks(String fileName,
			List<Category> categoryList) throws Exception {
		int depth = 3;

		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(fileName));
		// step 3
		document.open();

		Chapter chapter = null;
		int chNo = 1;
		for (Category cateCH : categoryList) {
			chapter = new Chapter(new Paragraph(cateCH.getName(),
					BlogUtil.PDF_FONT_CH), chNo++);
			for (Category cateSub : cateCH.getSubCateMap().values()) {
				Section section = chapter.addSection(new Paragraph(cateSub
						.getName(), BlogUtil.PDF_FONT_SEC));
				section.setBookmarkTitle(cateSub.getName());
				section.setIndentation(10);
				section.setIndentationLeft(10);
				section.setBookmarkOpen(false);
				section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				BlogUtil.recursionCategory(cateSub, section);
				// for (Category cateSub2 : cateSub.getSubCateList()) {
				// Section subsection = section.addSection(new Paragraph(
				// cateSub2.getName(), MyContants.PDF_CN_FONT));
				// subsection.setBookmarkTitle(cateSub2.getName());
				// subsection.setIndentation(10);
				// subsection.setIndentationLeft(10);
				// subsection.setBookmarkOpen(false);
				// subsection
				// .setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				// }
				// for (BlogInfo vo : cateSub.getBlogList()) {
				// Section subsection = section.addSection(new Paragraph(
				// new Chunk(vo.getTitle(), MyContants.PDF_CN_FONT)
				// .setUnderline(0.2f, -2f).setAnchor(
				// vo.getBlogURL())));
				// subsection.setBookmarkTitle(vo.getTitle());
				// subsection.setIndentation(10);
				// subsection.setIndentationLeft(10);
				// subsection.setBookmarkOpen(false);
				// subsection
				// .setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				// }
			}
			document.add(chapter);
		}

		document.close();
	}

	public static void testBookMarks(String fileName) throws Exception {

		BaseFont bfCN = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
				false);
		Font fontCN = new Font(bfCN, 12, Font.NORMAL,
				new BaseColor(0, 204, 255));
		int depth = 3;

		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(fileName));
		// step 3
		document.open();

		Chapter chapter = new Chapter(new Paragraph("主类：语言", fontCN), 1);

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

}

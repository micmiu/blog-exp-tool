package com.micmiu.blogtool.pdf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.WritableElement;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.micmiu.blogtool.utils.BlogUtil;
import com.micmiu.blogtool.utils.MyContants;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class ItextPDFHandler implements IPDFHandler {

	private static final Logger logger = Logger
			.getLogger(ItextPDFHandler.class);

	public void buildBlog2PDF(Document document,
			Collection<Category> categoryList) throws Exception {
		Paragraph p_line = new Paragraph(" ");
		p_line.add(MyContants.PDF_LINE);

		Chapter chapter = null;
		int chNo = 1;
		for (Category cateCH : categoryList) {
			logger.debug(">>: category = " + cateCH.getName());
			chapter = new Chapter(new Paragraph(cateCH.getName(),
					BlogUtil.PDF_FONT_CH), chNo++);
			for (Category cateSub : cateCH.getSubCateMap().values()) {
				Section section = chapter.addSection(new Paragraph(cateSub
						.getName(), BlogUtil.PDF_FONT_SEC));
				// section.setBookmarkTitle(cateSub.getName());
				section.setIndentation(10);
				section.setIndentationLeft(10);
				section.setBookmarkOpen(false);
				section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				// 递归生成
				recursionCategory(document, section, cateSub);
			}

			Collections.sort(cateCH.getBlogList());

			for (BlogInfo vo : cateCH.getBlogList()) {
				Section subsection = chapter.addSection(new Paragraph(
						new Chunk(vo.getTitle(), BlogUtil.PDF_FONT_TITLE)
								.setUnderline(0.2f, -2f).setAnchor(
										vo.getBlogURL())));
				// subsection.setBookmarkTitle(vo.getTitle());
				subsection.setIndentation(10);
				subsection.setIndentationLeft(10);
				subsection.setBookmarkOpen(false);
				subsection
						.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				subsection.add(new Paragraph(new Chunk(vo.getPostDate(),
						BlogUtil.PDF_FONT_TITLE)));
				subsection.add(p_line);
				if (null != vo.getContent() && !"".equals(vo.getContent())) {
					logger.debug("parse blog >>: " + vo.getTitle());
					try {
						subsection.addAll(parseHtml2Element(BlogUtil
								.parse2Stream(vo.getContent())));
					} catch (Exception e) {
						logger.error("CH section add element error:", e);
					}

				}
			}
			document.add(chapter);
		}
	}

	public void buildBlog2PDF(Document document, BlogInfo vo) throws Exception {
		Chapter chapter = new Chapter(new Paragraph(vo.getCategoryTag(),
				BlogUtil.PDF_FONT_CH), 1);
		Section section = chapter.addSection(new Paragraph(new Chunk(vo
				.getTitle(), BlogUtil.PDF_FONT_TITLE).setUnderline(0.2f, -2f)
				.setAnchor(vo.getBlogURL())));

		Paragraph p_line = new Paragraph("发表日期: " + vo.getPostDate(),
				BlogUtil.PDF_FONT_CN);
		p_line.add(MyContants.PDF_LINE);
		section.add(p_line);
		section.addAll(parseHtml2Element(BlogUtil.parse2Stream(vo.getContent())));
		section.add(Chunk.NEWLINE);
		document.add(chapter);
	}

	/**
	 * 
	 * @param cate
	 * 
	 * @param section
	 */
	private void recursionCategory(Document document, Section section,
			Category cate) {
		logger.debug(">>: recursionCategory = " + cate.getName());
		for (Category cateSub : cate.getSubCateMap().values()) {
			Section subsection = section.addSection(new Paragraph(cateSub
					.getName(), BlogUtil.PDF_FONT_SEC));
			// subsection.setBookmarkTitle(cateSub.getName());
			subsection.setIndentation(10);
			subsection.setIndentationLeft(10);
			subsection.setBookmarkOpen(false);
			subsection
					.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			recursionCategory(document, subsection, cateSub);
		}
		for (BlogInfo vo : cate.getBlogList()) {
			Section subsection = section.addSection(new Paragraph(new Chunk(vo
					.getTitle(), BlogUtil.PDF_FONT_TITLE).setUnderline(0.2f,
					-2f).setAnchor(vo.getBlogURL())));
			// subsection.setBookmarkTitle(vo.getTitle());
			subsection.setIndentation(10);
			subsection.setIndentationLeft(10);
			subsection.setBookmarkOpen(false);
			subsection
					.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			Paragraph p_line = new Paragraph("发表日期: " + vo.getPostDate()
					+ " 分类: " + vo.getCategoryTag(), BlogUtil.PDF_FONT_CN);
			p_line.add(MyContants.PDF_LINE);
			subsection.add(p_line);
			if (null != vo.getContent() && !"".equals(vo.getContent())) {
				logger.debug("parse sub blog >>: " + vo.getTitle());
				try {
					subsection.addAll(parseHtml2Element(BlogUtil
							.parse2Stream(vo.getContent())));
				} catch (Exception e) {
					logger.error("subsection add element error:", e);
				}
			}
			subsection.add(Chunk.NEWLINE);
		}
	}

	public List<Element> parseHtml2Element(InputStream ins) {
		try {
			final List<Element> eleList = new ArrayList<Element>();
			ElementHandler elemH = new ElementHandler() {

				public void add(final Writable w) {
					if (w instanceof WritableElement) {
						eleList.addAll(((WritableElement) w).elements());
					}
				}
			};
			InputStreamReader isr = new InputStreamReader(ins, "UTF-8");
			XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);

			List<Element> list = new ArrayList<Element>();
			for (Element ele : eleList) {
				if (ele instanceof LineSeparator
						|| ele instanceof WritableDirectElement) {
					continue;
				}
				list.add(ele);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public void parseHtml2PDF(Document document, PdfWriter pdfwriter,
			InputStream ins) {
		try {
			InputStreamReader isr = new InputStreamReader(ins, "UTF-8");

			// XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document,
			// isr);

			HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
			htmlContext.charSet(Charset.forName("UTF-8"));
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

			// ImageProvider
			// htmlContext.setImageProvider(new AbstractImageProvider() {
			// public String getImageRootPath() {
			// return "d:/test/itext/img";
			// }
			// });

			// LinkProvider
			// htmlContext.setLinkProvider(new LinkProvider() {
			// public String getLinkRoot() {
			// return dir+"/pdftemplate/";
			// }
			//
			// });

			CSSResolver cssResolver = XMLWorkerHelper.getInstance()
					.getDefaultCssResolver(true);
			Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
					new HtmlPipeline(htmlContext, new PdfWriterPipeline(
							document, pdfwriter)));
			XMLWorker worker = new XMLWorker(pipeline, true);
			XMLParser p = new XMLParser();
			p.addListener(worker);
			p.parse(isr);
			p.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

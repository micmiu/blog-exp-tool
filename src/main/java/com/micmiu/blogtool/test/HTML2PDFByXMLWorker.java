package com.micmiu.blogtool.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.BaseFont;
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
import com.micmiu.blogtool.html.IHtmlHandler;
import com.micmiu.blogtool.html.JsoupHtmlHandler;
import com.micmiu.blogtool.utils.FileUtil;

public class HTML2PDFByXMLWorker {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		HTML2PDFByXMLWorker
				.testPDFByElement("http://www.micmiu.com/opensource/expdoc/itext-url-pdf/");

		// FileInputStream ins = new FileInputStream(new File(rootPath +
		// htmlFile));
		//
		// InputStreamReader isr = new InputStreamReader(ins, "utf-8");
		// int b;
		// while ((b = isr.read()) != -1) {
		// System.out.print((char) b);
		// }
		// isr.close();
	}

	public static void testPDFByFile() throws Exception {
		String rootPath = "d:/test/itext/";
		String htmlFile = "blog-2.html";
		String pdfFile = rootPath + "blog-2.pdf";

		HTML2PDFByXMLWorker hander = new HTML2PDFByXMLWorker();
		long times = System.currentTimeMillis();
		System.out.println(" --------------- > start...");
		FileInputStream ins = new FileInputStream(new File(rootPath + htmlFile));
		hander.buildHTML2PDF(ins, pdfFile);
		System.out.println(" --------------- > end. use time(ms):"
				+ (System.currentTimeMillis() - times));
	}

	public static void testPDFByHTML(String blogURL) throws Exception {
		IHtmlHandler hander = new JsoupHtmlHandler();
		String rootPath = "d:/test/itext/";
		String[] infoArr = hander.extractBlogInfo(blogURL);
		System.out.println(" title    >>: " + infoArr[0]);
		System.out.println(" categroy >>: " + infoArr[1]);
		String pdfFile = rootPath + FileUtil.parseFileName(infoArr[0]) + ".pdf";
		HTML2PDFByXMLWorker pdfHandler = new HTML2PDFByXMLWorker();
		pdfHandler.buildHTML2PDF(pdfHandler.parse2Stream(infoArr[3]), pdfFile);

	}

	public static void testPDFByElement(String blogURL) throws Exception {
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

	public InputStream parse2Stream(String content) throws Exception {
		ByteArrayInputStream stream = new ByteArrayInputStream(
				content.getBytes("utf-8"));
		return stream;

	}

	public void buildHTML2PDF(InputStream ins, String pdfFile) {
		try {
			Document document = new Document(PageSize.A4);
			// document.setMargins(0, 0, 0, 0);

			// pdf method
			FileOutputStream outputStream = new FileOutputStream(pdfFile);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
			// pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
			document.open();

			InputStreamReader isr = new InputStreamReader(ins, "UTF-8");

			BaseFont bfCN = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			Font fontCN = new Font(bfCN, 12, Font.NORMAL, BaseColor.GREEN);
			Chapter chapter = new Chapter(new Paragraph("大类：语言", fontCN), 1);

			Section section = chapter.addSection(new Paragraph("子类：java",
					fontCN));
			section.setNumberDepth(1);
			section.setBookmarkTitle("子类：java");
			// section.setIndentation(30);
			section.setIndentationLeft(20);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Paragraph("内容如下：", fontCN));
			LineSeparator line = new LineSeparator(1, 100, new BaseColor(204,
					204, 204), Element.ALIGN_CENTER, -2);
			Paragraph p_line = new Paragraph(" ");
			p_line.add(line);
			section.add(p_line);

			// XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document,
			// isr);

			HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
			htmlContext.charSet(Charset.forName("UTF-8"));
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			// final String dir =
			// request.getSession().getServletContext().getRealPath("/");
			// htmlContext.setImageProvider(new AbstractImageProvider() {
			// public String getImageRootPath() {
			// return dir+"/pdftemplate/"; //web realpath for images.
			// }
			// });
			//
			// htmlContext.setLinkProvider(new LinkProvider() {
			// public String getLinkRoot() {
			// return dir+"/pdftemplate/";
			// }
			//
			// });

			CSSResolver cssResolver = XMLWorkerHelper.getInstance()
					.getDefaultCssResolver(true);
			// Document document2 = new Document(PageSize.A4);
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// PdfWriter pdfWriter2 = PdfWriter.getInstance(document2, baos);

			HtmlPipeline htmlpipe = new HtmlPipeline(htmlContext,
					new PdfWriterPipeline(document, pdfWriter));

			Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
					htmlpipe);
			XMLWorker worker = new XMLWorker(pipeline, true);
			XMLParser p = new XMLParser();
			p.addListener(worker);
			p.parse(isr);
			p.flush();

			section = chapter.addSection(new Paragraph("子类2：Ruby", fontCN));
			// section.setIndentation(30);
			section.setIndentationLeft(20);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Paragraph("内容如下：", fontCN));
			section.add(p_line);

			document.add(chapter);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void buildHTML2Element(InputStream ins, String pdfFile) {
		try {
			Document document = new Document(PageSize.A4);
			// document.setMargins(0, 0, 0, 0);

			// pdf method
			FileOutputStream outputStream = new FileOutputStream(pdfFile);
			PdfWriter pdfwriter = PdfWriter.getInstance(document, outputStream);
			// pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
			document.open();

			InputStreamReader isr = new InputStreamReader(ins, "UTF-8");

			BaseFont bfCN = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			Font fontCN = new Font(bfCN, 12, Font.NORMAL, BaseColor.GREEN);
			Chapter chapter = new Chapter(new Paragraph("大类：语言", fontCN), 1);

			Section section = chapter.addSection(new Paragraph("子类：java",
					fontCN));
			section.setNumberDepth(1);
			section.setBookmarkTitle("子类：java");
			// section.setIndentation(30);
			section.setIndentationLeft(20);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Paragraph("内容如下：", fontCN));
			LineSeparator line = new LineSeparator(1, 100, new BaseColor(204,
					204, 204), Element.ALIGN_CENTER, -2);
			Paragraph p_line = new Paragraph(" ");
			p_line.add(line);
			section.add(p_line);

			// WritableElement w = new WritableElement();
			// ElementHandler eh = new ElementList();
			// eh.add(w);

			final List<Element> eleList = new ArrayList<Element>();
			ElementHandler elemH = new ElementHandler() {

				public void add(final Writable w) {
					if (w instanceof WritableElement) {
						eleList.addAll(((WritableElement) w).elements());
					}

				}
			};

			XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);
			System.out.println(eleList.size());
			List<Element> list = new ArrayList<Element>();
			for (Element ele : eleList) {
				if (ele instanceof LineSeparator
						|| ele instanceof WritableDirectElement) {
					continue;
				}
				System.out.println(ele.getClass());
				list.add(ele);
			}
			section.addAll(list);

			section = chapter.addSection(new Paragraph("子类2：Ruby", fontCN));
			// section.setIndentation(30);
			section.setIndentationLeft(20);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Paragraph("内容如下：", fontCN));
			section.add(p_line);

			document.add(chapter);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

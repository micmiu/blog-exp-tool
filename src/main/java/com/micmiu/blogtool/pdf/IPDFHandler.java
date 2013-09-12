/**
 * 
 */
package com.micmiu.blogtool.pdf;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface IPDFHandler {

	void buildBlog2PDF(Document document, Collection<Category> categoryList)
			throws Exception;

	void buildBlog2PDF(Document document, BlogInfo vo) throws Exception;

	List<Element> parseHtml2Element(InputStream ins);

	void parseHtml2PDF(Document document, PdfWriter pdfwriter, InputStream ins);

}

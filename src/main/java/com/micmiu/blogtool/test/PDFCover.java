package com.micmiu.blogtool.test;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.micmiu.blogtool.utils.MyContants;

public class PDFCover {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String pdfFile = "d:/test/itext/pdfcover.pdf";
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
			
			
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

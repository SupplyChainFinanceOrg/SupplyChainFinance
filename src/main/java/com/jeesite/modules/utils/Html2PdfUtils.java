package com.jeesite.modules.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

public class Html2PdfUtils {
	public static void testHtml2Pdf(String html) throws Exception{
		//指定PDF的存放路径
		String outputFile = "D:/tes111111111222t.pdf";
		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		//指定字体。为了支持中文字体
		fontResolver.addFont("font/arialunicodems.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		renderer.setDocumentFromString(html);
		// 解决图片的相对路径问题
		//renderer.getSharedContext().setBaseURL("file:/Users/hongzhenyue/Desktop/backup/spring_boot_demo/src/main/resources/file");
		renderer.layout();
		renderer.createPDF(os);
		renderer.finishPDF();
		renderer = null;
		os.close();
	}
	// 支持中文
	public static boolean htmlToPdfString(String html,String pdfPath,String ttcPath)  {
		try {
			OutputStream os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
			//支持中文
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(ttcPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(html);
			// 解决图片的相对路径问题
			// renderer.getSharedContext().setBaseURL("file:/F:/teste/html/");
			renderer.layout();
			renderer.createPDF(os);
			System.out.println("转换成功!");
			os.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void htmlToPdfFile(String htmlPath,String pdfPath) throws Exception{
		OutputStream os = new FileOutputStream(pdfPath);
		ITextRenderer renderer = new ITextRenderer();
		//支持中文
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		String url = new File( htmlPath ).toURI().toURL().toString();
		renderer.setDocument(url);
		renderer.layout();
		renderer.createPDF(os);
		os.close();
	}
}

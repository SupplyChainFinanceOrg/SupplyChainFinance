package com.jeesite.modules.contact;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.jeesite.modules.contact.service.TbContractService;
import com.jeesite.modules.utils.Html2PdfUtils;
import com.jeesite.modules.utils.Utils;
import com.lowagie.text.pdf.BaseFont;

@Controller
@RequestMapping(value = "${adminPath}/contract")
public class ContactController {

	@Autowired
	private TbContractService contractsService;
	@Value("${fontPath}")
	private  String fontPath;


	@RequestMapping(value = {"contractMain"})
	public String contractMain(HttpServletResponse response,Model model) {
		System.err.println(fontPath);
		String printStr=contractsService.get(1+"").getTempContent();
		printStr=printStr.replaceAll("companyNameOfSale", "大雁金服");
		model.addAttribute("printStr", printStr);
		String savePath="D:/pdfString.pdf";
		try {
			if(Html2PdfUtils.htmlToPdfString(printStr,savePath,fontPath)){
				//上传 savePath 到上上签
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "modules/contract/contractMain";
	}

	public static void main(String[] args) {
		
	}
	public static boolean WriteStringToFile(String filePath,String str) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(str);// 往文件里写入字符串
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

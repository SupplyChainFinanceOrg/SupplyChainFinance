package com.jeesite.modules.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
public class Utils {
	/**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	/**
     * 转换字符集到utf8
     */
    public static String convertToUtf8(String src) {
        if (src == null || src.length() == 0) {
            return src;
        }
        byte[] srcData = src.getBytes();

        try {
            return new String(srcData, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从utf8转换字符集
     */
    public static String convertFromUtf8(String src) {
        if (src == null || src.length() == 0) {
            return src;
        }
        byte[] srcData = new byte[0];
        try {
            srcData = src.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(srcData);
    }

    public static String urlEncode(String data) throws UnsupportedEncodingException {
        String newData = Utils.convertToUtf8(data);
        return URLEncoder.encode(newData, "UTF-8");
    }

    public static String join(String[] items, String split) {
        if (items.length == 0) {
            return "";
        }
        StringBuffer s = new StringBuffer();
        int i;
        for (i = 0; i < items.length - 1; i++) {
            s.append(items[i]);
            s.append(split);
        }
        s.append(items[i]);
        return s.toString();
    }

    public static int rand(int min, int max) {
        return (int) ((max - min + 1) * Math.random() + min);
    }

	/**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String file2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String returnStr="";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println(tempString);
            	returnStr=returnStr+tempString+"\n";
            }
            reader.close();
            return returnStr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return "";
    }
    public void htmlCodeComeFromFile(String filePath, String pdfPath) {  
        Document document = new Document();  
        try {  
            StyleSheet st = new StyleSheet();  
            st.loadTagStyle("body", "leading", "16,0");  
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));  
            document.open();  
            List<Element> p = HTMLWorker.parseToList(new FileReader(filePath), st);  
            for(int k = 0; k < p.size(); ++k) {  
                document.add((Element)p.get(k));  
            }  
            document.close();  
            System.out.println("文档创建成功");  
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void htmlCodeComeString(String htmlCode, String pdfPath) {  
        Document doc = new Document(PageSize.A4);  
        try {  
            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));  
            doc.open();  
            // 解决中文问题  
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);  
            Paragraph t = new Paragraph(htmlCode, FontChinese);  
            doc.add(t);  
            doc.close();  
            System.out.println("文档创建成功");  
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  
public static void main(String[] args) {
	deleteFile("d:/contract/uploadpdf/20180511/1/保理合同_1526007866601.pdf");
}
}

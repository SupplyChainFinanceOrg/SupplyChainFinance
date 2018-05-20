package com.jeesite.modules.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import org.springframework.web.bind.annotation.RequestMapping;

import com.jeesite.common.lang.StringUtils;



public class ZipfileUtile {

	//批量下载
			@RequestMapping(params = "downzips")
			public static HttpServletResponse downzips(String[] files,String zipname,HttpServletRequest request, HttpServletResponse response) {
				String	filepath ="";
				if(files!=null&&files.length>0){
					for(String path:files){
						
						if(StringUtils.isNotBlank(path)){	
							try {
								path=URLDecoder.decode(path,"utf-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							filepath= new File(path).getParent();
							break;
						}
					}
				}
				String zippath="";
				try {
					zippath = URLDecoder.decode((filepath+File.separator+zipname+".zip").replace("|", ""),"utf-8");
					//zippath = (filepath+File.separator+zipname+".zip").replace("|", "");

				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File file = new File(zippath);					
				try {
								if (!file.exists()){     
									file.createNewFile();				
					            }
							//	response.reset();
								 //创建文件输出流
					            FileOutputStream fous;							
								fous = new FileOutputStream(file);								 
					            /**打包的方法我们会用到ZipOutputStream这样一个输出流,
					             * 所以这里我们把输出流转换一下*/
					           ZipOutputStream zipOut  = new ZipOutputStream(fous);
					            /**这个方法接受的就是一个所要打包文件的集合，
					             * 还有一个ZipOutputStream*/		           
					           zipFile(files, zipOut);
					            zipOut.close();
					            fous.close();					     
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response=downloadZip(file,response,request);
				return response;       
				
			}
			  /**
		     * 把接受的全部文件打成压缩包 
		     * @param List<File>;  
		     * @param org.apache.tools.zip.ZipOutputStream  
		     */
		    public static void zipFile (String[] files,ZipOutputStream outputStream) {
		        int size = files.length;
		        for(int i = 0; i < size; i++) {
		        	if(StringUtils.isNotEmpty(files[i])){
        			try {
						files[i]=URLDecoder.decode(files[i],"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
		        	if(StringUtils.isNotEmpty(files[i])&&new File(files[i]).exists()){ 
		        		try {

		        		StringBuffer tStringBuffer = new StringBuffer();
		    			InputStream inStream = new FileInputStream(files[i]);
		    			InputStreamReader fileInputStreamReader = new InputStreamReader(inStream,"utf-8"); 
		    			BufferedReader tBufferedReader = new BufferedReader(fileInputStreamReader);
		    			String sTempOneLine = new String("");
		    			while ((sTempOneLine = tBufferedReader.readLine()) != null){
		    				tStringBuffer.append(sTempOneLine);
		    				//System.out.println(sTempOneLine);
		    			}
		    			/*String s = tStringBuffer.toString();
		    			PrintWriter pfp= null;
		    			
		    			pfp=new PrintWriter(files[i]);
		    			pfp.print(s);
		    			
		    			//关闭流
		    			if(null!=pfp){
		    				pfp.close();
		    			}*/
		    			if(null!=inStream){
		    				inStream.close();	
		    			}
		    			if(null!=fileInputStreamReader){
		    				fileInputStreamReader.close();			
		    					}
		    			if(null!=tBufferedReader){
		    				tBufferedReader.close();			
		    			}
						zipFile(new File(files[i]), outputStream);
		        		} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		
		        	}
		        }
		    }
			
			/**  
		     * 根据输入的文件与输出流对文件进行打包
		     * @param File
		     * @param org.apache.tools.zip.ZipOutputStream
		     */
		    public static void zipFile(File inputFile,
		            ZipOutputStream ouputStream) {
		        try {
		            if(inputFile.exists()) {
		                /**如果是目录的话这里是不采取操作的，
		                 * 至于目录的打包正在研究中*/
		                if (inputFile.isFile()) {
		                    FileInputStream IN = new FileInputStream(inputFile);
		                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
		                    //org.apache.tools.zip.ZipEntry
		                    ZipEntry entry = new ZipEntry(inputFile.getName());
		                    ouputStream.putNextEntry(entry);
		                    // 向压缩文件中输出数据   
		                    int nNumber;
		                    byte[] buffer = new byte[512];
		                    while ((nNumber = bins.read(buffer)) != -1) {
		                        ouputStream.write(buffer, 0, nNumber);
		                    }
		                    // 关闭创建的流对象   
		                    bins.close();
		                    IN.close();
		                } else {
		                    try {
		                        File[] files = inputFile.listFiles();
		                        for (int i = 0; i < files.length; i++) {
		                            zipFile(files[i], ouputStream);
		                        }
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		    public static HttpServletResponse downloadZip(File file,HttpServletResponse response,HttpServletRequest request) {
		        try {
		        // 以流的形式下载文件。
		        InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
		        byte[] buffer = new byte[fis.available()];
		        fis.read(buffer);
		        fis.close();
		        // 清空response
		        response.reset();

		        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		        response.setContentType("application/zip");
		        ///response.setCharacterEncoding("utf-8");
		        //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
		        String  filenames=new String(file.getName().getBytes(), "ISO8859-1");
		        System.out.println(request.getHeader("user-agent"));
		        if (request.getHeader("user-agent").toLowerCase().contains("msie")||request.getHeader("user-agent").toLowerCase().contains("trident")) {  
		            // IE  
		        	filenames = URLEncoder.encode(file.getName(), "UTF-8");  
		         }  
		        response.setHeader("Content-Disposition", "attachment;filename=" +filenames);
		        toClient.write(buffer);
		        toClient.flush();
		        toClient.close();
		        } catch (IOException ex) {
		        ex.printStackTrace();
		        }finally{
		             try {
		                    File f = new File(file.getPath());
		                    f.delete();
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		        }
		        return response;
		    }
public static void main(String[] args) {
	  try {
		String  filenames=new String("还好".getBytes(), "ISO8859-1");
		System.out.println(filenames);
		System.out.println(new String(filenames.getBytes("ISO8859-1"), "utf-8"));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

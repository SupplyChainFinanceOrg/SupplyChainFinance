package com.jeesite.modules.contract.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;


public class ContarctUtils {

	/**
	 * 创建合同编号
	 * @return
	 */
	public static String createContarctsCode(String contarctCode,String number){
		String[] codes=contarctCode.split("_");
		SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
		contarctCode=codes[0]+sdf.format(new Date())+codes[1]+number+codes[2];
		return contarctCode;
	}

	/**
	 * 合同日期算法
	 * @param date
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Date addDate(Date date,long day) throws ParseException {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		time+=day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}
	/**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static long getNowTimeStamp(long expireTime) {
    	expireTime=expireTime*24*60*60;
        long time = System.currentTimeMillis();
        time = time / 1000+expireTime;
        return time;
    }
    public static void main(String[] args) {
		System.err.println(getNowTimeStamp(7));
	}
    public static void downloadNet(HttpServletResponse response,String url,String downPath) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL uri = new URL(url);
        URLConnection conn;
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
             conn = uri.openConnection();
             inStream = conn.getInputStream();
             fs = new FileOutputStream(downPath);

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
				fs.flush();
				fs.close();
	        	inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		}
    }
}

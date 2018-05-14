package com.jeesite.modules.contract.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

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
}

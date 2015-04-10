package com.ihouse.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	public static String formatDate(String pattern, Date date){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String formatDate(String pattern){
		return formatDate(pattern, new Date());
	}
	
	public static Date toDate(String pattern, String str){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		Date date=null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}

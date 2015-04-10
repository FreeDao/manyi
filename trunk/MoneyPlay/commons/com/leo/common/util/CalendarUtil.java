package com.leo.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	/**
	 *
	 * @param myDate
	 * @param pattern  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Date myDate,String... pattern) {
		String _pattern = pattern.length>0?pattern[0]:"yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(_pattern);
		return formatter.format(myDate);
	}
	
	public static Date toDate(String myDate,String... pattern) {
		String _pattern = pattern.length>0?pattern[0]:"yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(_pattern);
		try {
			return formatter.parse(myDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 返回YYYYMM格式的整型
	 * @param myDate
	 * @return
	 */
	public static int format1(Date myDate)
	{
		try
		{
			String date = format(myDate);
			StringBuffer sb = new StringBuffer();
			sb.append(date.substring(0,4));
			sb.append(date.substring(5, 7));
			return Integer.parseInt(sb.toString());
		}
		catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * convert unix_time to string object
	 * chenmeizhen 2009.05.23 
	 */
	public static String unixtime2string(int sec,String... pattern){
		String _pattern = pattern.length>0?pattern[0]:"yyyy-MM-dd HH:mm:ss";
		String result = null;
		try{
			Date d = (new Date(sec*1000L));
		    SimpleDateFormat sdf =  new SimpleDateFormat(_pattern);
		    Calendar c1 = Calendar.getInstance(); 
		    c1.setTime(d);
		    result = sdf.format(c1.getTime());
		}
		catch(Exception e){
			
		}	
		return result;
	}
	

}

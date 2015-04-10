package com.manyi.ihouse.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IObjectUtils {
	
	/**
	 * Object转换成String类型，obj==null，返回空字符串
	 * @param object 
	 * @return
	 */
	public static String convertString(Object object){
		if(object == null){
			return "";
		}
		if(object.toString().length() > 0){
			return object.toString();
		} else {
			return "";
		}
	}
	
	
	
	/**
	 * Object转换成String类型，如果为null或者空，返回format
	 * @param object
	 * @param format
	 * @return
	 */
	public static String convertString(Object object,String format){
		if(object == null){
			return format;
		}
		if(object.toString().length() > 0){
			return object.toString();
		} else {
			return format;
		}
	}
	
	/**
	 * BigDecimal 转 long
	 * @param obj
	 * @return
	 */
//	public static long convertLong(Object obj){
//		if(obj == null){
//			return 0;
//		}
//		return new BigDecimal(obj.toString()).longValue();
//	}
//	
	/**
	 * Object 转 float
	 * @param obj
	 * @return
	 */
	
	public static float convertFloat(Object obj){
		if(obj == null){
			return 0;
		}
		try {
			return Float.parseFloat(obj.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
	public static int convertInt(Object obj){
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	public static long convertLong(Object obj){
		if(obj == null){
			return 0;
		}
		return Long.parseLong(obj.toString());
	}
	
	
	/**
	 * 转换经纬度 返回:经纬度 *100000000的long型
	 * @param obj
	 * @return
	 */
//	public static long convertLonLat(Object obj){
//		if(obj == null){
//			return 0;
//		}
//		
//		try {
//			return Long.parseLong(obj.toString());
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			return 0;
//		}
//	}
	
	/**
	 * OBJECT 转换double
	 * @param obj
	 * @return
	 */
	public static double convertDouble(Object obj){
		if(obj == null){
			return 0;
		}
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
	/**
	 * obj - date
	 * @param obj
	 * @return
	 */
	public static Date convertDate(Object obj){
		Date date = new Date();
		if(obj == null || "0".equals(obj.toString())){
			return null;
		}
		
		try {
			 date = DateUtil.string2date(obj.toString(), "yyyy-MM-dd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return date;
	}
	
	public static String floatConvertString(Object obj){
		if(obj == null){
			return "";
		}
		String str = obj.toString();
		int len = str.lastIndexOf(".");
		return str.substring(0, len);
	}
	
//	public static void main(String[] args){
//		System.out.println(convertLonLat2("121.451111"));
//	}
	
}

package com.leo.common.util;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {
	public static String toFullHex(byte data){
        int val = ((int)data) & 0xff;
        
        if (val < 16)
            return "0"+Integer.toHexString(val);
         else
           return Integer.toHexString(val);
	}
	
	public static String toHex(int value,int bit){
		String temp = Integer.toHexString(value);
		for(int i=temp.length();i<bit;i++){
			temp = "0" + temp;
		}
		return temp;
	}
	
	public static String toString(Serializable[] sers,String... separator){
		String result = "";
		String _separator = separator.length>0?separator[0]:",";
		if(sers!=null && sers.length>0){
			for(Serializable ser:sers){
				if(ser!=null)
				    result = result + ser + _separator;
			}
			result = result.substring(0,result.length()-1);
		}
		
		return result;
	}
	
	public static void main(String args[]){
//		Float[] data = new Float[]{1f,2f,3f,4f,5f,6f,7f,8f};
//		System.out.println(toString(data));
//		for (int j = 0; j < 100; j++) {
//			System.out.println(createRandomAlphanum(6));
//		}
//		System.out.println(checkMobile(""));
		
//		for (int j = 0; j < 100; j++) {
//			System.out.println(createRandomNum(3));
//		}
		
		System.out.println(checkNumAndLetter(1, 3, "58k"));
		
		System.out.println(checkNum(3,4,"7"));
	}
	
	/**
	 * 验证 输入的字符串 必须是纯数字
	 * start 起始长度 为0 表示不限制长度(end 也为0)
	 * end 结束长度
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static boolean checkNum(int start , int end , String value){
		String xeg = "\\d{"+start+","+end+"}";
		if(start == 0 && end ==0){
			xeg ="\\d*";
		}
		Pattern patternTel = Pattern.compile(xeg);
		Matcher matcherTel =patternTel.matcher(value);
		return matcherTel.matches();
	}
	
	/**
	 * 正则 检查 手机号的合法性
	 * 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
	 * @param mobile
	 * @return true 通过; false 不通过
	 */
	public static boolean checkMobile(String mobile){
		//电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		Pattern patternTel = Pattern.compile("1[3,5,8]\\d{9}");
		Matcher matcherTel =patternTel.matcher(mobile);
		return matcherTel.matches();
	}
	
	/**
	 * 正则 检查 身份证号码的合法性
	 * 身份证号码 15数字  ,18(18位数字, 17位数字+1个X) 
	 * @param mobile
	 * @return true 通过; false 不通过
	 */
	public static boolean checkCode(String code){
		//身份证号码 15数字  ,18(18位数字, 17位数字+1个X) 
		Pattern pattCode = Pattern.compile("\\d{15}(\\d{2}[0-9X])?");
		Matcher matcherCode =  pattCode.matcher(code);
		return matcherCode.matches();
	}
	
	/**
	 * 检验 一定范围长度的 字母+数字 组合
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static boolean checkNumAndLetter(int start , int end,String value){
		if(value == null){
			return false;
		}
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{"+start+","+end+"}$";
		return	value.matches(regex);
	}
	
	/**
	 * 随机生产 len 位长度的 字母+数字的组合
	 * @param len 生产字符串的长度
	 * @return 生产的字母数字组合
	 */
	public static String createRandomAlphanum(int len){
		final  String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A",
				"S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M" };

		String s = "";
		for (int i = 0; i < len; i++) {
			int a = (int) (Math.random() * strs.length);
			s += strs[a];
		}
		return s;
	}
	
	/***
	 * 随机产生6为数字随机数
	 */
	public static long createRandom(){
		return (long)Math.round(Math.random() * 100000 + 99999);
	}
	
	/**
	 * 产生 len 位 随机 数字(字符串)
	 * @param len 生产随机数字的长度
	 * @return 
	 */
	public static String createRandomNum(int len){
		final  String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String s = "";
		for (int i = 0; i < len; i++) {
			int a = (int) (Math.random() * strs.length);
			s += strs[a];
		}
		return s;
	}
	
    /**
	 * URL-encodes a string.
	 */
	public static String escape(String s,String...enc) {
		if (s == null)
			return null;

		try {
			return URLEncoder.encode(s, enc.length>0?enc[0]:"UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    /**
	 * URL-decodes a string.
	 */
    public static String unescape(String s,String...enc) {
        if (s == null)  return null;
        
        try {
            return URLDecoder.decode(s, enc.length>0?enc[0]:"UTF-8");
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
    
    public static Map<String,String> splitQueryString(String queryString){
    	Map<String,String> result = new HashMap<String,String>(0);
    	String[] keyValuePairs = queryString.split("\\&");
    	for(String keyValuePair:keyValuePairs){
    		String[] keyValue = keyValuePair.split("=");
    		result.put(keyValue[0], keyValue[1]);
    	}
    	return result;
    }
}

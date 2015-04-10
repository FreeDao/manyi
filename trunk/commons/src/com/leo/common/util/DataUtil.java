package com.leo.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
	
	/**
	 * Long BigDecimal BigInteger 转为int
	 * @param numObj
	 * @return
	 */
	public static int toInt(Object numObj){
		int num =0;
		if(numObj == null){
			return num;
		}
		if(numObj instanceof Long){
			num = ((Long)numObj).intValue();
		}else if(numObj instanceof BigDecimal){
			num = ((BigDecimal)numObj).intValue();
		}else if(numObj instanceof BigInteger){
			num = ((BigInteger)numObj).intValue();
		}else if(numObj instanceof Integer){
			num = ((Integer)numObj).intValue();
		}
		return num;
	}
	
	// 判定输入汉字

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	public static boolean isChinese(String str) {
		//String name ="股份eee111eQ7676都会好的88电话费12433";
		
		char[] cTemp = str.toCharArray();
		boolean res = true;
		for (int i = 0; i < str.length(); i++) {
			if (!isChinese(cTemp[i])&&!checkNum(0, 0, cTemp[i]+"") && !checkLetter(0,0,cTemp[i]+"")) {
				res = false;
				break;
			}
		}
		return res;
	}
	// 检测String是否全是中文

	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
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
		
//		System.out.println(checkNumAndLetter(1, 3, "58k"));
//		
//		System.out.println(checkNum(3,4,"7"));
//		String email = "ygduanwenpingqq.com";
//		System.out.println(checkEmail(email));
//		System.out.println(checkLetter(0,0,"hfhfh"));
		
		String name ="股份eee111eQ7676都会好的88、、电话费12433";
		
//		char[] cTemp = name.toCharArray();
//		boolean res = true;
//		for (int i = 0; i < name.length(); i++) {
//			if (isChinese(name)&&!checkNum(0, 0, cTemp[i]+"") && !checkLetter(0,0,cTemp[i]+"")) {
//				res = false;
//				break;
//			}
//		}
//		System.out.println(res);
		
		System.out.println(isChinese(name));
	}
	
	/**
	 * 验证 输入的字符串 必须是字母(包含大小写)
	 * start 起始长度 为0 表示不限制长度(end 也为0)
	 * end 结束长度
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static boolean checkLetter(int start , int end , String value){
		String xeg = "^[A-Za-z]+${"+start+","+end+"}";
		if(start == 0 && end ==0){
			xeg ="^[A-Za-z]+$";
		}
		Pattern patternTel = Pattern.compile(xeg);
		Matcher matcherTel =patternTel.matcher(value);
		return matcherTel.matches();
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
		Pattern patternTel = Pattern.compile("1[3,5,8,7,4]\\d{9}");
		Matcher matcherTel =patternTel.matcher(mobile);
		return matcherTel.matches();
	}
	
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
		//  String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			String check = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 正则 检查 手机号的合法性
	 * 电话号码的验证   11位数字即可
	 * @param mobile
	 * @return true 通过; false 不通过
	 */
	public static boolean checkMobile11(String mobile){
		//电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		Pattern patternTel = Pattern.compile("\\d{11}");
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

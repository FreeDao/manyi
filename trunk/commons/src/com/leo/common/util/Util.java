/**
 * 
 */
package com.leo.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author leili
 * 
 */
public abstract class Util {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T manyChangeOne(Collection c) {
		if (c == null || c.size() < 1)
			return null;
		else {
			return (T) c.toArray(new Object[c.size()])[0];
		}

	}

	/**
	 * 根据给定的分隔符拆分字符串成字符
	 * 
	 * @author mzchen
	 * @since 2.0 2008.05.12
	 * @param s
	 *            待拆分的字符串
	 * @param sep
	 *            分隔符
	 * @return 如果字符串为null,空串或者只有一个spe的串,则都返回长度为0的String[]; <br/>
	 *         此方法不会返回null,故外部无需判断null
	 */
	public static String[] splitString(String s, String sep) {
		if (s == null)
			return new String[0];
		s = s.trim();
		if (s != null && s.length() > 0 && !(s.length() == 1 && s.equals(sep))) {
			return s.split(sep);
		} else {
			return new String[0];
		}
	}

	/**
	 * 根据给定的分隔符拆分字符串成int数组
	 * 
	 * @author mzchen
	 * @since 2.0 2008.05.12
	 * @param s
	 *            待拆分的字符串
	 * @param sep
	 *            分隔符
	 * @return 如果字符串为null,空串或者只有一个spe的串,则都返回长度为0的int[]; <br/>
	 *         此方法不会返回null,故外部无需判断null
	 */
	public static int[] splitString2Int(String s, String sep) {
		String[] ss = splitString(s, sep);
		int[] result = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	/**
	 * 根据给定的分隔符拆分字符串成long数组
	 * 
	 * @author hjh
	 * @since 2.0 2008.05.12
	 * @param s
	 *            待拆分的字符串
	 * @param sep
	 *            分隔符
	 * @return 如果字符串为null,空串或者只有一个spe的串,则都返回长度为0的int[]; <br/>
	 *         此方法不会返回null,故外部无需判断null
	 */
	public static long[] splitString2long(String s, String sep) {
		String[] ss = splitString(s, sep);
		long[] result = new long[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Long.parseLong(ss[i]);
		}
		return result;
	}

	/**
	 * 根据给定的分隔符拆分字符串成Integer数组
	 * 
	 * @author leili
	 * @since 2.0 2008.05.13
	 * @param s
	 *            待拆分的字符串
	 * @param sep
	 *            分隔符,默认为','此参数可以为空
	 * @return 如果字符串为null,空串或者只有一个spe的串,则都返回长度为0的int[]; <br/>
	 *         此方法不会返回null,故外部无需判断null
	 */
	public static Integer[] splitString2Integer(String s, String... sep) {
		String _sep = sep.length > 0 ? sep[0] : ",";
		String[] ss = splitString(s, _sep);
		Integer[] result = new Integer[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	public static int getMaxNameNumber(List<String> names, String prefix) {
		int maxValue = -1;
		for (String name : names) {
			try {
				String temp = name.toLowerCase().replaceFirst(prefix.toLowerCase(), "");
				int value = Integer.parseInt(temp.length() > 0 ? temp.trim() : "0");
				if (value >= maxValue)
					maxValue = value;
			} catch (NumberFormatException e) {
				;
			}
		}
		return maxValue;
	}

	public static String getMaxName(List<String> names, String prefix) {
		int maxValue = getMaxNameNumber(names, prefix);
		return maxValue == -1 ? null : (maxValue == 0 ? prefix : (prefix + maxValue));
	}

	public static String generateSequenceName(List<String> udns, String prefix) {
		int maxValue = getMaxNameNumber(udns, prefix);
		return maxValue == -1 ? prefix : prefix + (maxValue + 1);
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * add by chenmeizhen,2009.04.08
	 * 
	 * @param max
	 * @return
	 */
	public static int randomInt(int max) {
		Random rand = new Random();
		return Math.round((rand.nextFloat() * max));

	}

	public static void printInputStream(InputStream inStream) {
		StringBuilder sb = new StringBuilder();
		byte[] bb = new byte[1024];
		int len = 0;
		try {
			while ((len = inStream.read(bb)) > 0) {
				sb.append(new String(bb, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(sb.toString());
	}

	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

}

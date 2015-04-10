package com.manyi.hims.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 发布时间格式： 1小时内:XX分钟前 24小时内：XX小时前 7天内：X天前 超过7天：显示日期
	 * @param date
	 * @return
	 */
	public static String fattrDate(Date date) {
		long time = System.currentTimeMillis() - date.getTime();// 得到 当前时间的毫秒数
		double hours = time / 1000 / 60 / 60d;// 小时
		String fattrStr="";
		if ((hours / 24) >= 7) {
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy年MM月dd日");
			fattrStr =sdf.format(date);
		} else if (hours < 1) {
			double t = (time / 1000 / 60d);
			fattrStr =(t <= 1 ? "1": Math.round(t))+ "分钟前发布";
		} else if (1 <= hours && hours < 24) {
			fattrStr =  Math.round(hours) + "小时前发布";
		} else if ((hours / 24) >= 1 && (hours / 24) < 7) {
			double  t = (hours / 24d);
			fattrStr = (t <= 1 ? "1" : Math.round(t) ) + "天前发布";
		}
		return fattrStr;
	}
}

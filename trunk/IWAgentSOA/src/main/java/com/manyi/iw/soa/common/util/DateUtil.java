package com.manyi.iw.soa.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 发布时间格式： 1小时内:XX分钟前 24小时内：XX小时前 7天内：X天前 超过7天：显示日期
     * @param date
     * @return
     */
    public static String fattrDate(Date date) {
        long time = System.currentTimeMillis() - date.getTime();// 得到 当前时间的毫秒数
        double hours = time / 1000 / 60 / 60d;// 小时
        String fattrStr = "";
        if ((hours / 24) >= 7) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            fattrStr = sdf.format(date);
        }
        else if (hours < 1) {
            double t = (time / 1000 / 60d);
            fattrStr = (t <= 1 ? "1" : Math.round(t)) + "分钟前发布";
        }
        else if (1 <= hours && hours < 24) {
            fattrStr = Math.round(hours) + "小时前发布";
        }
        else if ((hours / 24) >= 1 && (hours / 24) < 7) {
            double t = (hours / 24d);
            fattrStr = (t <= 1 ? "1" : Math.round(t)) + "天前发布";
        }
        return fattrStr;
    }

    /**
     * 发布时间描述 当日：今日发布 ；  其他： XX天前发布
     * @param smdate
     * @return
     */
    public static String pubDateFormat(Date smdate) {
        Date bdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        String formatStr = "";

        if (between_days == 0) {
            formatStr = "今日发布";
        }
        else {
            formatStr = between_days + "天前发布";
        }

        return formatStr;
    }

    /**
     * 
     * 功能描述:将制定格式的字符串转为Date类型
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @param dateString
     * @param formatStr
     * @return
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return formateDate;
    }

    public static String date2string(Date date, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 
     * 功能描述:格式化日期字符串显示格式
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @param sdate
     *              原始日期格式 s - 表示 "yyyy-mm-dd" 形式的日期的 String 对象
     * @param format
     *              格式化后日期格式
     * @return 格式化后的日期显示
     */
    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        String dateString = formatter.format(date);

        return dateString;
    }

    /**
     * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
     *
     * @param year
     * @param month
     *            month是从1开始的12结束
     * @param day
     * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
     */
    public static int getDayOfWeek(String year, String month, String day) {
        Calendar cal = new GregorianCalendar(new Integer(year).intValue(), new Integer(month).intValue() - 1,
                new Integer(day).intValue());
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
     *
     * @param date
     *            "yyyy/MM/dd",或者"yyyy-MM-dd"
     * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
     */
    public static int getDayOfWeek(String date) {
        String[] temp = null;
        if (date.indexOf("/") > 0) {
            temp = date.split("/");
        }
        if (date.indexOf("-") > 0) {
            temp = date.split("-");
        }
        return getDayOfWeek(temp[0], temp[1], temp[2]);
    }

    /**
     * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
     *
     * @param date
     *
     * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
     *
     * @param date
     *            格式为 yyyy/MM/dd 或者 yyyy-MM-dd
     * @return 返回当前日期是星期几
     */
    public static String getChinaDayOfWeek(Date date) {
        String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int week = getDayOfWeek(date);
        return weeks[week - 1];
    }

    /**
     * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
     *
     * @param date
     *            格式为 yyyy/MM/dd 或者 yyyy-MM-dd
     * @return 返回当前日期是星期几
     */
    public static String getChinaDayOfWeek(String date) {
        String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int week = getDayOfWeek(date);
        return weeks[week - 1];
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.string2date("2014-06-20 15:12:18", "yyyy-MM-dd HH:mm:ss").getTime());
        //        System.out.println(DateUtil.date2string(d1, "yyyy/MM/dd"));
        //        System.out.println(DateUtil.date2string(d1, "HH:mm"));
        //        System.out.println(DateUtil.date2string(new Date(), "yyyy年MM月dd日"));

    }
}

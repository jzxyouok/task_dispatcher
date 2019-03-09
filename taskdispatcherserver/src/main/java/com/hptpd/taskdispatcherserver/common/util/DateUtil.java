package com.hptpd.taskdispatcherserver.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Date Operate Class
 *
 * @author Tracy4262
 * @version jquery.0
 * @since 2012-03-30
 */
public class DateUtil {

    /**
     * 默认的格式: yyyy-MM-dd HH:mm:ss
     */
    public static String FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式: yyyyMMddHHmmss
     */
    public static String FMT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 格式: yyyyMMddHH
     */
    public static String FMT_YYYYMMDDHH = "yyyyMMddHH";
    /**
     * 格式: yyyyMMdd
     */
    public static String FMT_YYYYMMDD = "yyyyMMdd";

    public static String FMT_MMDDYYYY = "MM/dd/yyyy";

    /**
     * 格式: yyyy-MM-dd
     */
    public static String FMT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式转为字符串格式[格式：yyyy-MM-dd HH:mm:ss]
     *
     * @param date ：传入的日期
     * @return ：String
     */
    public static String dateToString(Date date) {
        SimpleDateFormat simpDateFormat = new SimpleDateFormat(FMT_DEFAULT);
        return simpDateFormat.format(date);
    }

    /**
     * 日期格式转为字符串格式
     *
     * @param date ：传入的日期
     * @param fmt  ：转为字符的格式
     * @return ：String
     */
    public static String dateToString(Date date, String fmt) {
        SimpleDateFormat simpDateFormat = new SimpleDateFormat(fmt);
        return simpDateFormat.format(date);
    }

    /**
     * 字符串格式转日期
     *
     * @param str ：传入的字符串
     * @return ：Date
     */
    public static Date stringToDate(String str) {
        SimpleDateFormat simpDateFormat = new SimpleDateFormat(FMT_DEFAULT);
        try {
            return simpDateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式不正确");
        }
    }

    /**
     * 字符串格式转日期
     *
     * @param str ：传入的字符串
     * @param fmt ：转为的日期格式
     * @return ：Date
     */
    public static Date stringToDate(String str, String fmt) {
        if (!str.isEmpty()) {
            SimpleDateFormat simpDateFormat = new SimpleDateFormat(fmt);
            try {
                return simpDateFormat.parse(str);
            } catch (ParseException e) {
                throw new RuntimeException("日期格式不正确");
            }
        } else {
            return DateUtil.getTime();
        }

    }

    /**
     * 获取当前字符串日期格式[yyyy-MM-dd]
     *
     * @return
     */
    public static String getTodayYyyyMmDd() {
        return DateUtil.dateToString(DateUtil.getTime(),
                DateUtil.FMT_YYYY_MM_DD);
    }

    /**
     * 获取当前字符串日期格式[yyyyMMdd]
     *
     * @return
     */
    public static String getTodayyyyyMMdd() {
        return DateUtil.dateToString(DateUtil.getTime(),
                DateUtil.FMT_YYYYMMDD);
    }

    /**
     * 获取当前时间[yyyy-MM-dd HH:mm:ss]
     *
     * @return
     */
    public static String getStrTime() {
        return dateToString(getTime(), DateUtil.FMT_DEFAULT);
    }

    public static String getStrTimeYYYYMMDDHHMMSS() {
        return dateToString(getTime(), DateUtil.FMT_YYYYMMDDHHMMSS);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getTime() {
        return new Date();
    }

    /**
     * 在指定日期上添加指定的月份
     *
     * @param date   ：传入的日期
     * @param amount ：添加的天数
     * @return
     */
    public static Date addMonths(Date date, Integer amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 在指定日期上添加指定的天数
     *
     * @param date   ：传入的日期
     * @param amount ：添加的天数
     * @return
     */
    public static Date addDays(Date date, Integer amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 在指定日期上添加指定的小时数
     *
     * @param date   ：传入的日期
     * @param amount ：添加的小时
     * @return
     */
    public static Date addHours(Date date, Integer amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 在指定日期上添加指定的分钟
     *
     * @param date   ：传入的日期
     * @param amount ：添加的分钟
     * @return
     */
    public static Date addMinutes(Date date, Integer amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * 在指定日期上添加指定的秒数
     *
     * @param date   ：传入的日期
     * @param amount ：添加的秒数
     * @return
     */
    public static Date addSeconds(Date date, Integer amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 获取时间差
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param type      时间的类型[0秒/1分钟/2小时]
     * @return
     */
    public static Long getDateDiff(Date beginTime, Date endTime, Integer type) {
        long time = 0;
        long diffTime = endTime.getTime() - beginTime.getTime();
        switch (type) {
            case 0:
                time = diffTime / 1000;
                break;
            case 1:
                time = diffTime / (60 * 1000);
                break;
            case 2:
                time = diffTime / (60 * 60 * 1000);
                break;
            default:

        }
        return time;
    }

    /**
     * 获取两个日期之间的所有日期（yyyy-MM-dd）
     *
     * @param begin
     * @param end
     * @return
     * @Description TODO
     */
    public static List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = Lists.newArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
            /* Calendar tempEnd = Calendar.getInstance();
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            tempEnd.setTime(end);
            while (tempStart.before(tempEnd)) {
                result.add(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }*/
        while (begin.getTime() <= end.getTime()) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**
     * 比较两个日期的先后大小
     *
     * @param source
     * @param target
     * @return
     */
    public static Boolean isGreaterThan(Date source, Date target) {
        Boolean flag = false;

        return flag;
    }
}
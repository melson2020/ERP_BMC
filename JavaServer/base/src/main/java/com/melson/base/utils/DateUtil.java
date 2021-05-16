package com.melson.base.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author wuhuan
 * @date 2021年04月18日
 */
public class DateUtil {


    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_SHORT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter MINUTE_SHORT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter TIME_SHORT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 时间转字符串:yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String timeFormat(Date date) {
        return format(date, TIME_FORMATTER);
    }

    /**
     * 时间转字符串:yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String timeShortFormat(Date date) {
        return format(date, TIME_SHORT_FORMATTER);
    }

    private static String format(Date date, DateTimeFormatter dtf) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return dtf.format(ldt);
    }

    /**
     * 字符串转时间
     *
     * @param timeStr
     * @return
     */
    public static Date formatTime(String timeStr) {
        LocalDateTime ldt = LocalDateTime.parse(timeStr, TIME_FORMATTER);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}

package com.example.demo02app.util;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class DateTimeUtil {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_TIME_H_M = "HH:mm";

    public static final SimpleDateFormat SDF = new SimpleDateFormat(PATTERN, Locale.CHINA);
    public static final SimpleDateFormat SDF_YEAR = new SimpleDateFormat(PATTERN_DATE, Locale.CHINA);
    public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat(PATTERN_DATE, Locale.CHINA);
    public static final SimpleDateFormat SDF_H_M = new SimpleDateFormat(PATTERN_TIME_H_M, Locale.CHINA);

    public static final TimeZone TZ = TimeZone.getDefault();
    public static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @StringDef({PATTERN_DATE, PATTERN_TIME})
    public @interface PATTERN {
    }

    /**
     * 获取当前时间
     *
     * @return 时间戳
     */
    public static long getCurrentTimeStamp() {
        return new Date().getTime();
    }

    public static long getTimeMillionUTC(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance(TZ_UTC);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 时间戳转字符串
     *
     * @param timestamp 时间戳
     * @return 日期-字符串
     */
    public static String getDateString(long timestamp) {
        return SDF_YEAR.format(new Date(timestamp));
    }

    /**
     * 时间戳转字符串
     *
     * @param timestamp 时间戳
     * @return 时间-字符串
     */
    public static String getTimeString(long timestamp) {
        return SDF_TIME.format(new Date(timestamp));
    }

    public static String getTimeString(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(timestamp);
    }

    public static String getTimeStringUTC(long timestamp) {
        SDF_H_M.setTimeZone(TZ_UTC);
        return SDF_H_M.format(new Date(timestamp));
    }


    /**
     * 时间戳转字符串
     *
     * @param timestamp 时间戳
     * @return 字符串
     */
    public static String getDateAndTimeStringUTC(long timestamp) {
        SDF.setTimeZone(TZ_UTC);
        return SDF.format(timestamp);
    }

    public static String getDateAndTimeString(long timestamp) {
        SDF.setTimeZone(TZ);
        return SDF.format(timestamp);
    }


    /**
     * 字符串转时间戳
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static long getTimestamp(String str) throws ParseException {
        SDF.setTimeZone(TZ_UTC);
        return Objects.requireNonNull(SDF.parse(str)).getTime();
    }

    public static long toUTC(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        Calendar calendar1 = Calendar.getInstance(TZ_UTC);
        calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        return calendar1.getTimeInMillis();
    }

}

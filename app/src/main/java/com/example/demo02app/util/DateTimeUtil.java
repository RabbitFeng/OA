package com.example.demo02app.util;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";

    public static final SimpleDateFormat SDF = new SimpleDateFormat(PATTERN, Locale.CHINA);
    public static final SimpleDateFormat SDF_YEAR = new SimpleDateFormat(PATTERN_DATE, Locale.CHINA);
    public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat(PATTERN_DATE, Locale.CHINA);

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

    /**
     * 时间戳转字符串
     *
     * @param timestamp 时间戳
     * @return 字符串
     */
    public static String getDateAndTimeString(long timestamp) {
        return SDF.format(timestamp);
    }

    /**
     * 字符串转时间戳
     * @param str
     * @return
     * @throws ParseException
     */
    public static long getTimestamp(String str) throws ParseException {
        Date parse = SDF.parse(str);
        return parse.getTime();
    }

}

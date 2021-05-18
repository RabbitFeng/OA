package com.example.demo02app.util.converter;

import androidx.databinding.InverseMethod;

import com.example.demo02app.util.DateTimeUtil;

import java.text.ParseException;

public class DateTimeConverter {
    @InverseMethod("convertToTime")
    public static String convertToString(long time) {
        return DateTimeUtil.getDateAndTimeStringUTC(time);
    }

    public static String toHourAndMinute(long time) {
        return DateTimeUtil.getTimeStringUTC(time);
    }

    public static long convertToTime(String timeStr) {
        try {
            return DateTimeUtil.getTimestamp(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

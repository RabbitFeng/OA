package com.example.demo02app.util.converter;

import androidx.databinding.InverseMethod;

import com.example.demo02app.util.DateTimeUtil;

public class DateTimeConverter {
    @InverseMethod("convertToTime")
    public static String convertToString(long time) {
        return DateTimeUtil.getDateAndTimeString(time);
    }

    public static long convertToTime(String timeStr) {
        return DateTimeUtil.getTimestamp(timeStr);
    }
}

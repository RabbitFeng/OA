package com.example.demo02app.util;

public class Converter {
    public String toDataAndTime(long timestamp){
        return DateTimeUtil.getDateAndTimeString(timestamp);
    }
}

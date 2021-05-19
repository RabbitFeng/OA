package com.example.demo02app.util;

import com.example.demo02app.model.schedule.data.ScheduleItem;
import com.haibin.calendarview.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarUtil {
    public static Calendar getSchemeCalendar(long timeInMillions) {
        java.util.Calendar instance = java.util.Calendar.getInstance();
        instance.setTimeInMillis(timeInMillions);

        int year = instance.get(java.util.Calendar.YEAR);
        int month = instance.get(java.util.Calendar.MONTH) + 1;
        int dayOfMonth = instance.get(java.util.Calendar.DAY_OF_MONTH);
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(dayOfMonth);
        calendar.setScheme("事");
        calendar.addScheme(new Calendar.Scheme());
        return calendar;
    }

    public static Map<String, Calendar> getSchemeMap(List<ScheduleItem> scheduleItems) {
        Map<String, Calendar> calendarMap = new HashMap<>();
        java.util.Calendar instance = java.util.Calendar.getInstance();

        for (ScheduleItem scheduleItem : scheduleItems) {
            instance.setTimeInMillis(scheduleItem.getTime());
            int year = instance.get(java.util.Calendar.YEAR);
            int month = instance.get(java.util.Calendar.MONTH) + 1;
            int dayOfMonth = instance.get(java.util.Calendar.DAY_OF_MONTH);

            Calendar calendar = new Calendar();
            calendar.setYear(year);
            calendar.setMonth(month);
            calendar.setDay(dayOfMonth);
            calendar.setScheme("事");
            calendar.addScheme(new Calendar.Scheme());
            calendarMap.put(calendar.toString(), calendar);
        }
        return calendarMap;
    }
}

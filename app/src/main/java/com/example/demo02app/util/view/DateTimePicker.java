package com.example.demo02app.util.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo02app.util.DateTimeUtil;

import java.util.Calendar;

public class DateTimePicker {

    private static final String TAG = DateTimePicker.class.getName();
    @Nullable
    private DateTimeCallback dateTimeCallback;

    private final TimePickerDialog timePickerDialog;
    private final DatePickerDialog datePickerDialog;

    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;

    public DateTimePicker(Context context) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            DateTimePicker.this.hourOfDay = hourOfDay;
            DateTimePicker.this.minute = minute;
            if (DateTimePicker.this.dateTimeCallback != null) {

                DateTimePicker.this.dateTimeCallback.onDateAndTimeSet(getTimeMillions());
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            DateTimePicker.this.year = year;
            DateTimePicker.this.month = month;
            DateTimePicker.this.dayOfMonth = dayOfMonth;
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private long getTimeMillions() {
//        Calendar calendar = Calendar.getInstance(DateTimeUtil.TZ_UTC);
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, 0);
        return DateTimeUtil.getTimeMillionUTC(year,month,dayOfMonth,hourOfDay,minute);
    }

    public void show(@NonNull DateTimeCallback callback) {
        this.dateTimeCallback = callback;
        datePickerDialog.show();
    }

    public interface DateTimeCallback {
        void onDateAndTimeSet(long timeMillions);
    }
}

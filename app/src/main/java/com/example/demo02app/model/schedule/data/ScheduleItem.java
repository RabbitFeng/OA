package com.example.demo02app.model.schedule.data;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class ScheduleItem {
    @ColumnInfo(name = "s_id")
    private int id;
    @ColumnInfo(name = "s_content")
    private String content;
    @ColumnInfo(name = "s_time")
    private long time;

    @Ignore
    public ScheduleItem() {
    }

    public ScheduleItem(int id, String content, long time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

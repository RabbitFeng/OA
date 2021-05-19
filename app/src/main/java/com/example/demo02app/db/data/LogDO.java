package com.example.demo02app.db.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "log")
public class LogDO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "l_id")
    private int id;
    @ColumnInfo(name = "l_content")
    private String content;

    @ColumnInfo(name = "l_time")
    private long time;

    @Ignore
    public LogDO() {
    }

    public LogDO(int id, String content, long time) {
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

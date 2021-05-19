package com.example.demo02app.db.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule")
public class ScheduleDO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "s_id")
    private int id;
    @ColumnInfo(name = "s_user_host")
    private String userHost;
    @ColumnInfo(name = "s_content")
    private String content;
    @ColumnInfo(name = "s_time")
    private long time;

    @Ignore
    public ScheduleDO() {
    }

    public ScheduleDO(int id, String userHost, String content, long time) {
        this.id = id;
        this.userHost = userHost;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(String userHost) {
        this.userHost = userHost;
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

package com.example.demo02app.db.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meeting")
public class MeetingDO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "m_id")
    private int id;

    @ColumnInfo(name = "m_user_host")
    private String userHost;

    @ColumnInfo(name = "m_user_publisher")
    private String userPublisher;

    @ColumnInfo(name = "m_title")
    private String title;

    @ColumnInfo(name = "m_begin_time")
    private long beginTime;

    @ColumnInfo(name = "m_end_time")
    private long endTime;

    @ColumnInfo(name = "m_address")
    private String address;

    @ColumnInfo(name = "m_time")
    private long publishTime;

    @Ignore
    public MeetingDO() {
    }

    public MeetingDO(int id, String userHost, String userPublisher, String title, long beginTime, long endTime, String address, long publishTime) {
        this.id = id;
        this.userHost = userHost;
        this.userPublisher = userPublisher;
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.address = address;
        this.publishTime = publishTime;
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

    public String getUserPublisher() {
        return userPublisher;
    }

    public void setUserPublisher(String userPublisher) {
        this.userPublisher = userPublisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}

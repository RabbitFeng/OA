package com.example.demo02app.model.meeting.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class MeetingDetail {
    @ColumnInfo(name = "m_id")
    private int id;

    @ColumnInfo(name = "ad_user_real_name")
    private String publisher;

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
    public MeetingDetail() {
    }

    public MeetingDetail(int id, String publisher, String title, long beginTime, long endTime, String address, long publishTime) {
        this.id = id;
        this.publisher = publisher;
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.address = address;
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "MeetingDetail{" +
                "id=" + id +
                ", publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", address='" + address + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

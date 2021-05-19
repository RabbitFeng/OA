package com.example.demo02app.model.meeting.data.entity;

public class MeetingItem {
    private int id;
    private String title;
    private long beginTime;
    private long endTime;
    private String address;

    public MeetingItem() {
    }

    public MeetingItem(int id, String title, long beginTime, long endTime, String address) {
        this.id = id;
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.address = address;
    }

    @Override
    public String toString() {
        return "MeetingItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", address='" + address + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

package com.example.demo02app.model.meeting.data.model;

public class MeetingItemView {
    private String title;
    private String beginTime;
    private String endTime;
    private String address;

    public MeetingItemView() {
    }

    public MeetingItemView(String title, String beginTime, String endTime, String address) {
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

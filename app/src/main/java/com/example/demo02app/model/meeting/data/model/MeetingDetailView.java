package com.example.demo02app.model.meeting.data.model;

public class MeetingDetailView {
    private String publisher;
    private String title;
    private String beginTime;
    private String endTime;
    private String address;
    private String publishTime;

    public MeetingDetailView() {
    }

    @Override
    public String toString() {
        return "MeetingDetailView{" +
                "publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", address='" + address + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }

    public MeetingDetailView(String publisher, String title, String beginTime, String endTime, String address, String publishTime) {
        this.publisher = publisher;
        this.title = title;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.address = address;
        this.publishTime = publishTime;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}

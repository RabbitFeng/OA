package com.example.demo02app.model.notice.data.model;

public class NoticeItemView {
    private String title;
    private String content;
    private String publisher;
    private String publishTime;

    public NoticeItemView() {
    }

    public NoticeItemView(String title, String content, String publisher, String publishTime) {
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}

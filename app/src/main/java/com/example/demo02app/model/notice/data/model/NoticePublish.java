package com.example.demo02app.model.notice.data.model;

public class NoticePublish {
    private String title;
    private String content;

    public NoticePublish() {
    }

    public NoticePublish(String title, String content) {
        this.title = title;
        this.content = content;
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
}

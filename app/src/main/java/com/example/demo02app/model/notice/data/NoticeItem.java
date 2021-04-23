package com.example.demo02app.model.notice.data;

/**
 * 公告
 */
public class NoticeItem {
    private String publisher;
    private String content;
    private String publishTime;

    public NoticeItem(String publisher, String content, String publishTime) {
        this.publisher = publisher;
        this.content = content;
        this.publishTime = publishTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}

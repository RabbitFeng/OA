package com.example.demo02app.model.notice.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

/**
 * 公告
 */
public class NoticeItem {
    @ColumnInfo(name = "n_id")
    private int id;
    @ColumnInfo(name = "n_title")
    private String title;
    @ColumnInfo(name = "n_content")
    private String content;

    @ColumnInfo(name = "ad_user_real_name")
    private String publisher;

    @ColumnInfo(name = "n_publish_time")
    private long publishTime;

    @Ignore
    public NoticeItem() {
    }

    @Override
    public String toString() {
        return "NoticeItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public NoticeItem(int id, String title, String content, String publisher, long publishTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.publishTime = publishTime;
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

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}

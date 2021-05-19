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
    private String publisherName;

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
                ", publisher='" + publisherName + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public NoticeItem(int id, String title, String content, String publisherName, long publishTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publisherName = publisherName;
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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}

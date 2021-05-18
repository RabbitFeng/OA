package com.example.demo02app.db.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notice")
public class NoticeDO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "n_id")
    @SerializedName("n_id")
    private int id;

    @ColumnInfo(name = "n_user_host")
    @SerializedName("n_user_host")
    private String userHost;

    @ColumnInfo(name = "n_title")
    @SerializedName("n_title")
    private String title;

    @ColumnInfo(name = "n_content")
    @SerializedName("n_content")
    private String content;

    @ColumnInfo(name = "n_publisher")
    @SerializedName("n_publisher")
    private String publisher;

    @ColumnInfo(name = "n_publish_time")
    @SerializedName("n_publish_time")
    private long publishTime;

    public NoticeDO() {
    }

    @Override
    public String toString() {
        return "NoticeDO{" +
                "id=" + id +
                ", userHost='" + userHost + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public NoticeDO(int id, String userHost, String title, String content, String publisher, long publishTime) {
        this.id = id;
        this.userHost = userHost;
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

    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(String userHost) {
        this.userHost = userHost;
    }
}

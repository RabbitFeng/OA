package com.example.demo02app.model.chat.entity;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class ChatMessageItem {
    @ColumnInfo(name = "cm_user_host")
    private String userHost;
    @ColumnInfo(name = "cm_user_other")
    private String userOther;
    @ColumnInfo(name = "cm_is_send")
    private boolean isSend;
    @ColumnInfo(name = "cm_content")
    private String content;
    @ColumnInfo(name = "cm_time")
    private long time;

    @Ignore
    public ChatMessageItem() {
    }

    @Override
    public String toString() {
        return "ChatMessageItem{" +
                "userHost='" + userHost + '\'' +
                ", userOther='" + userOther + '\'' +
                ", isSend=" + isSend +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    public ChatMessageItem(String userHost, String userOther, boolean isSend, String content, long time) {
        this.userHost = userHost;
        this.userOther = userOther;
        this.isSend = isSend;
        this.content = content;
        this.time = time;
    }

    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(String userHost) {
        this.userHost = userHost;
    }

    public String getUserOther() {
        return userOther;
    }

    public void setUserOther(String userOther) {
        this.userOther = userOther;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

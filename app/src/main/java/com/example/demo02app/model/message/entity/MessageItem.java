package com.example.demo02app.model.message.entity;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class MessageItem {
    @ColumnInfo(name = "cl_user_host")
    private String userHost;
    @ColumnInfo(name = "cl_user_other")
    private String userOther;
    @ColumnInfo(name = "ad_remark_name")
    private String remarkName;
    @ColumnInfo(name = "cl_is_send")
    private String isSend;
    @ColumnInfo(name = "cl_content")
    private String content;
    @ColumnInfo(name = "cl_latest_time")
    private long timeOfLatestMessage;

    @Ignore
    public MessageItem() {
    }

    public MessageItem(String userHost, String userOther, String remarkName, String isSend, String content, long timeOfLatestMessage) {
        this.userHost = userHost;
        this.userOther = userOther;
        this.remarkName = remarkName;
        this.isSend = isSend;
        this.content = content;
        this.timeOfLatestMessage = timeOfLatestMessage;
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

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeOfLatestMessage() {
        return timeOfLatestMessage;
    }

    public void setTimeOfLatestMessage(long timeOfLatestMessage) {
        this.timeOfLatestMessage = timeOfLatestMessage;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}

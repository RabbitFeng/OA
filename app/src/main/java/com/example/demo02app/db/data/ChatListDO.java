package com.example.demo02app.db.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * 聊天列表
 */
@Entity(tableName = "chat_list", primaryKeys = {"cl_user_other"})
public class ChatListDO {
    @ColumnInfo(name = "cl_user_host")
    private String userHost;

    @ColumnInfo(name = "cl_user_other")
    @NonNull
    private String userOther;

    @ColumnInfo(name = "cl_is_send")
    @NonNull
    private String isSend;

    @ColumnInfo(name = "cl_content")
    private String content;

    @ColumnInfo(name = "cl_latest_time")
    private long timeOfLatestMessage;

    public ChatListDO(String userHost, @NonNull String userOther, @NonNull String isSend, String content, long timeOfLatestMessage) {
        this.userHost = userHost;
        this.userOther = userOther;
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

    @NonNull
    public String getUserOther() {
        return userOther;
    }

    public void setUserOther(@NonNull String userOther) {
        this.userOther = userOther;
    }

    @NonNull
    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(@NonNull String isSend) {
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
}

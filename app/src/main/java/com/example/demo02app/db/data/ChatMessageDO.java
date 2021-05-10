package com.example.demo02app.db.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_message")
public class ChatMessageDO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cm_id")
    @NonNull
    private int id;

    @ColumnInfo(name = "cm_user_host")
    @NonNull
    private String userHost;

    @ColumnInfo(name = "cm_user_other")
    @NonNull
    private String userOther;

    @ColumnInfo(name = "cm_is_send")
    private boolean isSend;

    @ColumnInfo(name = "cm_content")
    @NonNull
    private String content;

    @ColumnInfo(name = "cm_time")
    private long time;

    @Ignore
    public ChatMessageDO() {
    }

    public ChatMessageDO(@NonNull String userHost, @NonNull String userOther, boolean isSend, @NonNull String content, long time) {
        this.userHost = userHost;
        this.userOther = userOther;
        this.isSend = isSend;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(@NonNull String userHost) {
        this.userHost = userHost;
    }

    @NonNull
    public String getUserOther() {
        return userOther;
    }

    public void setUserOther(@NonNull String userOther) {
        this.userOther = userOther;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

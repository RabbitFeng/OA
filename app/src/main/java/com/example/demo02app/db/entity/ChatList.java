package com.example.demo02app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

/**
 * 聊天列表
 */
@Entity(tableName = "chat_list",primaryKeys = {"cl_sender","cl_receiver"})
public class ChatList {
    @ColumnInfo(name = "cl_sender")
    @NonNull
    private String sender;

    @ColumnInfo(name = "cl_receiver")
    @NonNull
    private String receiver;

    @ColumnInfo(name = "cl_content")
    private String content;

    @ColumnInfo(name = "cl_latest_time")
    private long timeOfLatestMessage;

    public ChatList(@NotNull String sender, @NotNull String receiver, String content, long timeOfLatestMessage) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timeOfLatestMessage = timeOfLatestMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

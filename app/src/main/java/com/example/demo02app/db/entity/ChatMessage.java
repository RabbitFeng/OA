package com.example.demo02app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_message")
public class ChatMessage {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "cm_sender")
    private String sender;

    @ColumnInfo(name = "cm_receiver")
    private String receiver;

    @ColumnInfo(name = "cm_content")
    @NonNull
    private String content;

    @ColumnInfo(name = "cm_time")
    private long time;

    public ChatMessage(String sender, String receiver, @NonNull String content, long time) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

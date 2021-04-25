package com.example.demo02app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "chat_message")
public class ChatMessage {
    @ColumnInfo(name = "cm_from_id")
    @NonNull
    private String fromId;
    @ColumnInfo(name = "cm_to_id")
    @NonNull
    private String toId;
    @ColumnInfo(name = "cm_content")
    @NonNull
    private String content;
    @ColumnInfo(name = "cm_timestamp")
    @NonNull
    private long timestamp;

    public ChatMessage(String fromId, String toId, String content, long timestamp) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

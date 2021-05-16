package com.example.demo02app.model.chat.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessage {
    @Expose
    @SerializedName("user_sender")
    private String userSender;

    @Expose
    @SerializedName("user_receiver")
    private String userReceiver;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("timestamp")
    private long timestamp;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "userSender='" + userSender + '\'' +
                ", userReceiver='" + userReceiver + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public ChatMessage() {
    }

    public ChatMessage(@NonNull String userSender, @NonNull String receiver, @NonNull String content, long timestamp) {
        this.userSender = userSender;
        this.userReceiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(@NonNull String userSender) {
        this.userSender = userSender;
    }

    @NonNull
    public String getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(@NonNull String userReceiver) {
        this.userReceiver = userReceiver;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

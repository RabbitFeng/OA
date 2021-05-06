package com.example.demo02app.model.message.data;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class MessageItem {
    @NonNull
    private String from;
    private Bitmap profile;
    private String remarkName;
    private String latestMessage;
    private String timeOfLatestMessage;

    public MessageItem(@NonNull String from, Bitmap profile, String nickname, String latestMessage, String timeOfLatestMessage) {
        this.from = from;
        this.profile = profile;
        this.remarkName = nickname;
        this.latestMessage = latestMessage;
        this.timeOfLatestMessage = timeOfLatestMessage;
    }

    public Bitmap getProfile() {
        return profile;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public String getTimeOfLatestMessage() {
        return timeOfLatestMessage;
    }

    public void setTimeOfLatestMessage(String timeOfLatestMessage) {
        this.timeOfLatestMessage = timeOfLatestMessage;
    }
}

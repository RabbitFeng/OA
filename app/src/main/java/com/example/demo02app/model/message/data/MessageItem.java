package com.example.demo02app.model.message.data;

import android.graphics.Bitmap;

public class MessageItem {
    private Bitmap profile;
    private String nickname;
    private String lastMessage;
    private String timeOfMessage;

    public MessageItem(Bitmap profile, String nickname, String currentMessage, String timeOfMessage) {
        this.profile = profile;
        this.nickname = nickname;
        this.lastMessage = currentMessage;
        this.timeOfMessage = timeOfMessage;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimeOfMessage() {
        return timeOfMessage;
    }

    public void setTimeOfMessage(String timeOfMessage) {
        this.timeOfMessage = timeOfMessage;
    }
}

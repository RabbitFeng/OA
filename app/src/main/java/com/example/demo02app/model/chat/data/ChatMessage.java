package com.example.demo02app.model.chat.data;

public class ChatMessage {
    private String content;
    private String time;
    private boolean isSend;

    public ChatMessage(String content, String time, boolean isSend) {
        this.content = content;
        this.time = time;
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public boolean isSend() {
        return isSend;
    }
}

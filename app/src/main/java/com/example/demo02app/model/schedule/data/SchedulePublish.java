package com.example.demo02app.model.schedule.data;

public class SchedulePublish {
    private long timeInMillions;
    private String content;

    @Override
    public String toString() {
        return "SchedulePublish{" +
                "timeInMillions=" + timeInMillions +
                ", content='" + content + '\'' +
                '}';
    }

    public SchedulePublish() {
    }

    public SchedulePublish(long timeInMillions, String content) {
        this.timeInMillions = timeInMillions;
        this.content = content;
    }

    public long getTimeInMillions() {
        return timeInMillions;
    }

    public void setTimeInMillions(long timeInMillions) {
        this.timeInMillions = timeInMillions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

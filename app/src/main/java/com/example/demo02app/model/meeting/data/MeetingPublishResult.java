package com.example.demo02app.model.meeting.data;

import androidx.annotation.Nullable;

public class MeetingPublishResult {
    @Nullable
    private Integer error;

    public MeetingPublishResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}

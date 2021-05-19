package com.example.demo02app.model.notice;

import androidx.annotation.Nullable;

public class NoticePublishResult {
    @Nullable
    private Integer error;

    public NoticePublishResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}


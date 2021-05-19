package com.example.demo02app.model.notice.data;

import androidx.annotation.Nullable;

public class NoticePublishFormState {
    @Nullable
    private Integer error;

    private boolean valid;

    public NoticePublishFormState(@Nullable Integer error) {
        this.error = error;
        valid = false;
    }

    public NoticePublishFormState(boolean valid) {
        this.valid = valid;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public boolean isValid() {
        return valid;
    }
}

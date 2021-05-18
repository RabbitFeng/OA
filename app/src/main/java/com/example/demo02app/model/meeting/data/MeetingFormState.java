package com.example.demo02app.model.meeting.data;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class MeetingFormState {
    @Nullable
    @StringRes
    public  Integer error;

    public final boolean valid;

    public MeetingFormState(@Nullable Integer error) {
        this.error = error;
        valid = false;
    }

    public MeetingFormState(boolean valid) {
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

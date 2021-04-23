package com.example.demo02app.model.login.data;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.demo02app.model.login.data.model.LoggedInUser;


public class RegisterResult {
    @Nullable
    @StringRes
    private Integer error;

    @Nullable
    private LoggedInUser user;

    public RegisterResult(@Nullable Integer error) {
        this.error = error;
    }

    public RegisterResult(@Nullable LoggedInUser user) {
        this.user = user;
    }

    @Nullable
    public LoggedInUser getUser() {
        return user;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}

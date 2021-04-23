package com.example.demo02app.model.login.data;

import androidx.annotation.Nullable;

import com.example.demo02app.model.login.data.model.LoggedInUser;

public class LoginResult {
    @Nullable
    private Integer error;

    @Nullable
    private LoggedInUser loggedInUser;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}

package com.example.demo02app.model.login.data;

import androidx.annotation.StringRes;

public class LoginFormState {
    @StringRes
    private Integer usernameError;
    @StringRes
    private Integer passwordError;
    private boolean valid;

    public LoginFormState(Integer usernameError, Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        valid = false;
    }

    public LoginFormState(boolean valid) {
        this.valid = valid;
    }

    public Integer getUsernameError() {
        return usernameError;
    }

    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isValid() {
        return valid;
    }
}

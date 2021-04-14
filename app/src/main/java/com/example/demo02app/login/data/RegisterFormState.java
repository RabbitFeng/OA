package com.example.demo02app.login.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class RegisterFormState {
    @Nullable
    @StringRes
    private Integer usernameInvalid;
    @Nullable
    @StringRes
    private Integer passwordInvalid;

    @NonNull
    private boolean invalid;

    public RegisterFormState(@Nullable Integer usernameInvalid, @Nullable Integer passwordInvalid) {
        this.usernameInvalid = usernameInvalid;
        this.passwordInvalid = passwordInvalid;
        invalid = false;
    }

    public RegisterFormState(boolean invalid) {
        this.invalid = invalid;
    }

    @Nullable
    public Integer getUsernameInvalid() {
        return usernameInvalid;
    }

    @Nullable
    public Integer getPasswordInvalid() {
        return passwordInvalid;
    }

    public boolean isInvalid() {
        return invalid;
    }
}

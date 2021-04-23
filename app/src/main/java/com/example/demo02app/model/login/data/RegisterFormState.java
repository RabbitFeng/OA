package com.example.demo02app.model.login.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class RegisterFormState {
    @Nullable
    @StringRes
    private Integer phoneInvalid;
    @Nullable
    @StringRes
    private Integer passwordInvalid;

    @NonNull
    private boolean invalid;

    public RegisterFormState(@Nullable Integer phoneInvalid, @Nullable Integer passwordInvalid) {
        this.phoneInvalid = phoneInvalid;
        this.passwordInvalid = passwordInvalid;
        invalid = false;
    }

    public RegisterFormState(boolean invalid) {
        this.invalid = invalid;
    }

    @Nullable
    public Integer getPhoneInvalid() {
        return phoneInvalid;
    }

    @Nullable
    public Integer getPasswordInvalid() {
        return passwordInvalid;
    }

    public boolean isInvalid() {
        return invalid;
    }
}

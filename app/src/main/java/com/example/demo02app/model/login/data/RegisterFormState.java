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

    @Nullable
    @StringRes
    private Integer nameInvalid;

    @NonNull
    private boolean invalid;

    public RegisterFormState(@Nullable Integer phoneInvalid, @Nullable Integer passwordInvalid, @Nullable Integer nameInvalid) {
        this.phoneInvalid = phoneInvalid;
        this.passwordInvalid = passwordInvalid;
        this.nameInvalid = nameInvalid;
        invalid = !(phoneInvalid == null || passwordInvalid == null || nameInvalid == null);
    }


    public RegisterFormState(boolean invalid) {
        this.invalid = invalid;
    }

    @Nullable
    public Integer getPhoneInvalid() {
        return phoneInvalid;
    }

    public void setPhoneInvalid(@Nullable Integer phoneInvalid) {
        this.phoneInvalid = phoneInvalid;
    }

    @Nullable
    public Integer getPasswordInvalid() {
        return passwordInvalid;
    }

    public void setPasswordInvalid(@Nullable Integer passwordInvalid) {
        this.passwordInvalid = passwordInvalid;
    }

    @Nullable
    public Integer getNameInvalid() {
        return nameInvalid;
    }

    public void setNameInvalid(@Nullable Integer nameInvalid) {
        this.nameInvalid = nameInvalid;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}

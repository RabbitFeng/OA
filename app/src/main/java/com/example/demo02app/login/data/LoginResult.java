package com.example.demo02app.login.data;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoginResult {
    public static final int FAILURE = 0x00;
    public static final int USERNAME_NOT_EXIT = 0x01;
    public static final int PASSWORD_ERROR = 0x02;
    public static final int SUCCESS = 0x10;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({USERNAME_NOT_EXIT,PASSWORD_ERROR,SUCCESS,FAILURE})
    public @interface Result{
    }

    @Result
    @Nullable
    private Integer result;

    public LoginResult(@Nullable Integer result) {
        this.result = result;
    }

    @Nullable
    public Integer getResult() {
        return result;
    }
}

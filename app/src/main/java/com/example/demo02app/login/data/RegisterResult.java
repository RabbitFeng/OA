package com.example.demo02app.login.data;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class RegisterResult {
    public static final int FAILURE = 0x00;
    public static final int SUCCESS = 0x10;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({FAILURE, SUCCESS})
    public @interface Result {
    }

    @Result
    @Nullable
    private Integer result;

    public RegisterResult(@Nullable Integer result) {
        this.result = result;
    }

    @Result
    @Nullable
    public Integer getResult() {
        return result;
    }
}

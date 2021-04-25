package com.example.demo02app.model.login.data.model;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

/**
 * 注册用户
 */
public class RegisterUser {

    @NonNull
    private String phone;

    @NonNull
    private String password;

    @NonNull
    private String realName;

    @NonNull
    private int identity;


    public RegisterUser(@NonNull String phone, @NonNull String password, @NonNull String realName, int identity) {
        this.phone = phone;
        this.password = password;
        this.realName = realName;
        this.identity = identity;
    }

    @NotNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getPassword() {
        return password;
    }


    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getRealName() {
        return realName;
    }

    public void setRealName(@NonNull String realName) {
        this.realName = realName;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", identity=" + identity +
                '}';
    }
}

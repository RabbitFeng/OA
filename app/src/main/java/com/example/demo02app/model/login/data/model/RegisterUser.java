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


    public RegisterUser(String phone,  @NonNull String password) {
        this.phone = phone;
        this.password = password;
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

    @Override
    public String toString() {
        return "RegisterUser{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

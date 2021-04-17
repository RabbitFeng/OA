package com.example.demo02app.login.data.model;

import androidx.annotation.NonNull;

/**
 * 注册用户
 */
public class RegisterUser {

    /**
     * 手机号码
     */
    private String phone;

    @NonNull
    private String password;


    public RegisterUser(String phone,  @NonNull String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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

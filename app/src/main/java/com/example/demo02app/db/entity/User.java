package com.example.demo02app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class User {
    @NonNull
    @ColumnInfo(name = "u_id")
    private String id;
    @ColumnInfo(name = "u_pwd")
    private String pwd;
    @ColumnInfo(name = "u_phone")
    private String phone;
    @ColumnInfo(name = "identity")
    private int identity;

    public User(@NonNull String id, String pwd, String phone, int identity) {
        this.id = id;
        this.pwd = pwd;
        this.phone = phone;
        this.identity = identity;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}

package com.example.demo02app.model.addressbook.entity;

import androidx.room.ColumnInfo;

public class AddressBookItem {
    @ColumnInfo(name = "ad_user_other")
    private String userId;
    @ColumnInfo(name = "ad_remark_name")
    private String remarkName;

    public AddressBookItem(String userId, String remarkName) {
        this.userId = userId;
        this.remarkName = remarkName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    @Override
    public String toString() {
        return "AddressBookItem{" +
                "userId='" + userId + '\'' +
                ", remarkName='" + remarkName + '\'' +
                '}';
    }
}

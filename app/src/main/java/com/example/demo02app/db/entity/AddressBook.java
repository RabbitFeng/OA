package com.example.demo02app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "address_book", primaryKeys = {"ad_user_host", "ad_user_other"})
public class AddressBook {
    @ColumnInfo(name="ad_user_host")
    @NonNull
    private String userHost;

    @ColumnInfo(name = "ad_user_other")
    @NonNull
    private String userOther;

    // 备注
    @ColumnInfo(name = "ad_remark_name")
    private String remarkName;

    public AddressBook(@NonNull String userHost, @NonNull String userOther, String remarkName) {
        this.userHost = userHost;
        this.userOther = userOther;
        this.remarkName = remarkName;
    }

    @NonNull
    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(@NonNull String userHost) {
        this.userHost = userHost;
    }

    @NonNull
    public String getUserOther() {
        return userOther;
    }

    public void setUserOther(@NonNull String userOther) {
        this.userOther = userOther;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}

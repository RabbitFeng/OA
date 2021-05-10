package com.example.demo02app.db.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "address_book", primaryKeys = {"ad_user_host", "ad_user_other"})
public class AddressBookDO {
    @ColumnInfo(name="ad_user_host")
    @NonNull
    private String userHost;

    @ColumnInfo(name = "ad_user_other")
    @NonNull
    private String userOther;

    @ColumnInfo(name = "ad_remark_name")
    private String remarkName;

    @ColumnInfo(name = "ad_user_phone")
    private String userPhone;

    @ColumnInfo(name = "ad_user_real_name")
    private String realName;

    public AddressBookDO(@NonNull String userHost, @NonNull String userOther, String remarkName, String userPhone, String realName) {
        this.userHost = userHost;
        this.userOther = userOther;
        this.remarkName = remarkName;
        this.userPhone = userPhone;
        this.realName = realName;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

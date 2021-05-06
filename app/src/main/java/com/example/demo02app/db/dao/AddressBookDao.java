package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.demo02app.model.adressbook.data.AddressBookItem;

import java.util.List;

@Dao
public interface AddressBookDao {
    @Query("select * from address_book where ad_user_host = :uId")
    LiveData<List<AddressBookItem>> selectAddressBook(String uId);
}

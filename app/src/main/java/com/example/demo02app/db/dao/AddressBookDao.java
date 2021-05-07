package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.entity.AddressBook;
import com.example.demo02app.model.addressbook.data.AddressBookItem;

import java.util.List;

@Dao
public interface AddressBookDao {
    @Query("select * from address_book where ad_user_host = :uId order by ad_remark_name asc")
    LiveData<List<AddressBookItem>> selectAddressBook(String uId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAddressBook(List<AddressBook> addressBooks);
}

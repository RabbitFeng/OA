package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.AddressBookDO;
import com.example.demo02app.model.addressbook.model.AddressBookItem;

import java.util.List;

@Dao
public interface AddressBookDao {
    @Query("select * from address_book where ad_user_host = :userHost and ad_user_other!=:userHost order by ad_remark_name asc")
    LiveData<List<AddressBookDO>> selectAddressBook(String userHost);

    @Query("select * from address_book where ad_user_host=:userHost and ad_user_other=:userOther and ad_user_other!=:userHost")
    LiveData<AddressBookDO> selectAddressBookByUserId(String userHost, String userOther);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAddressBook(List<AddressBookDO> addressBooks);

    @Query("select ad_user_other,ad_remark_name " +
            "from address_book " +
            "where ad_user_host = :userHost and ad_user_other!=:userHost " +
            "order by ad_remark_name asc")
    LiveData<List<AddressBookItem>> selectAddressBookItem(String userHost);

    @Query("delete from address_book")
    int deleteAll();
}

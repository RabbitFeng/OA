package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.NoticeDO;
import com.example.demo02app.model.notice.data.model.NoticeItem;

import java.util.List;

@Dao
public interface NoticeDAO {
    @Query("select n_id,n_title,n_content,n_publish_time,ad_user_real_name " +
            "from notice " +
            "left join address_book " +
            "on n_publisher = ad_user_other " +
            "where n_user_host=:userHost")
    LiveData<List<NoticeItem>> queryAllNoticeItem(String userHost);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NoticeDO> noticeDOS);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoticeDO noticeDO);

    @Query("delete from notice where n_user_host=:userHost")
    void deleteAll(String userHost);

}

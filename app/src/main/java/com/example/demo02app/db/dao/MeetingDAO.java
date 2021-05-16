package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.MeetingDO;

import java.util.List;

@Dao
public interface MeetingDAO {
    @Query("select * from meeting where m_user_host=:userHost")
    LiveData<List<MeetingDO>> queryAllMeetingDO(String userHost);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MeetingDO> meetingDOS);


}

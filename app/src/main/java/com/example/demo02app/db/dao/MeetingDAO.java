package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.MeetingDO;
import com.example.demo02app.model.meeting.data.entity.MeetingDetail;

import java.util.List;

@Dao
public interface MeetingDAO {
    @Query("select * from meeting where m_user_host=:userHost")
    LiveData<List<MeetingDO>> queryAllMeetingDO(String userHost);

    @Query("select m.m_id,m.m_title,m.m_address,m.m_begin_time,m.m_end_time,m.m_time,a.ad_user_real_name " +
            "from meeting m " +
            "left join address_book a " +
            "on m.m_user_publisher = a.ad_user_other " +
            "where m.m_user_host = :userHost and m.m_id=:id")
    LiveData<MeetingDetail> queryMeetingDetail(String userHost, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MeetingDO> meetingDOS);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MeetingDO meetingDO);

    @Query("delete from meeting where m_user_host =:userHost")
    void deleteAll(String userHost);
}

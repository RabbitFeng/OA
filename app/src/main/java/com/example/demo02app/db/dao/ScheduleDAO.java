package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.ScheduleDO;

import java.util.List;

@Dao
public interface ScheduleDAO {
    @Query("select * from schedule where s_user_host =:userHost")
    LiveData<List<ScheduleDO>> queryAll(String userHost);

    @Query("select * from schedule where s_user_host = :userHost and s_id = :id")
    LiveData<ScheduleDO> query(String userHost, int id);

    @Query("select * from schedule where s_user_host=:userHost and s_time =  :time")
    LiveData<ScheduleDO> queryByTime(String userHost, long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<ScheduleDO> scheduleDOS);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ScheduleDO scheduleDO);
}

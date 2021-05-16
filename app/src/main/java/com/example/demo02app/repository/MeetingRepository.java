package com.example.demo02app.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.model.meeting.data.model.MeetingItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingRepository {
    private static final String TAG = MeetingRepository.class.getName();
    private final Context appContext;

    private final AppDatabase database;

    private final MyExecutors executors;

    private MeetingRepository(Context appContext, AppDatabase database, MyExecutors executors) {
        this.appContext = appContext;
        this.database = database;
        this.executors = executors;
    }

    private static MeetingRepository sInstance;

    public static MeetingRepository getInstance(Context context, AppDatabase database, MyExecutors executors) {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MeetingRepository(context.getApplicationContext(), database, executors);
                }
            }
        }
        return sInstance;
    }

    // 查询本地数据库
    public LiveData<List<MeetingItem>> loadAllMeeting(String userHost) {
        Log.d(TAG, "loadAllMeeting: called");
        MutableLiveData<List<MeetingItem>> listLiveData = new MutableLiveData<>();
        listLiveData.postValue(new ArrayList<MeetingItem>() {{
            add(new MeetingItem(0, "标题1", Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis(), "地址1"));
        }});

        return listLiveData;
//        return Transformations.map(database.meetingDAO().queryAllMeetingDO(userHost), input -> {
//            List<MeetingItem> meetingItems = new ArrayList<>(input.size());
//            for (MeetingDO meetingDO : input) {
//                MeetingItem item = new MeetingItem();
//                item.setId(meetingDO.getId());
//                item.setBeginTime(meetingDO.getBeginTime().getTime());
//                item.setEndTime(meetingDO.getEndTime().getTime());
//                item.setTitle(meetingDO.getTitle());
//                item.setAddress(meetingDO.getAddress());
//                meetingItems.add(item);
//            }
//            return meetingItems;
//        });
    }
}

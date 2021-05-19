package com.example.demo02app.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.demo02app.MyApplication;
import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.db.data.ScheduleDO;
import com.example.demo02app.model.schedule.data.ScheduleItem;
import com.example.demo02app.model.schedule.data.SchedulePublish;
import com.example.demo02app.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {
    private static final String TAG = ScheduleRepository.class.getName();
    @NonNull
    private final Application application;
    @NonNull
    private final AppDatabase database;
    @NonNull
    private final MyExecutors executors;

    private final String userHost;

    private ScheduleRepository(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        this.application = application;
        this.database = database;
        this.executors = executors;
        userHost = ((MyApplication) application).getUserId();
    }

    private static ScheduleRepository sInstance;

    public static ScheduleRepository getInstance(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (ScheduleRepository.class) {
                if (sInstance == null) {
                    sInstance = new ScheduleRepository(application, database, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<ScheduleItem>> loadScheduleItem() {
        return Transformations.map(database.scheduleDAO().queryAll(userHost), new Function<List<ScheduleDO>, List<ScheduleItem>>() {
            @Override
            public List<ScheduleItem> apply(List<ScheduleDO> input) {
                List<ScheduleItem> scheduleItems = new ArrayList<>(input.size());
                for (ScheduleDO scheduleDO : input) {
                    ScheduleItem scheduleItem = new ScheduleItem();
                    scheduleItem.setId(scheduleDO.getId());
                    scheduleItem.setContent(scheduleDO.getContent());
                    scheduleItem.setTime(scheduleDO.getTime());
                    scheduleItems.add(scheduleItem);
                }
                return scheduleItems;
            }
        });
    }

    public LiveData<SchedulePublish> loadSchedule(int id) {
        return Transformations.map(database.scheduleDAO().query(userHost, id), new Function<ScheduleDO, SchedulePublish>() {
            @Override
            public SchedulePublish apply(ScheduleDO input) {
                SchedulePublish schedulePublish = new SchedulePublish();
                if (input != null) {
                    schedulePublish.setTimeInMillions(DateTimeUtil.toLocal(input.getTime()));
                    schedulePublish.setContent(input.getContent());
                }
                return schedulePublish;
            }
        });
    }

    public LiveData<SchedulePublish> loadScheduleByTime(long time) {
        return Transformations.map(database.scheduleDAO().queryByTime(userHost, time), new Function<ScheduleDO, SchedulePublish>() {
            @Override
            public SchedulePublish apply(ScheduleDO input) {
                Log.d(TAG, "apply: called");
                SchedulePublish schedulePublish = new SchedulePublish();
                if (input != null) {
                    schedulePublish.setTimeInMillions(DateTimeUtil.toLocal(input.getTime()));
                    schedulePublish.setContent(input.getContent());
                }
                return schedulePublish;
            }
        });
    }

    public void addSchedule(SchedulePublish schedulePublish, RepositoryCallback<ScheduleDO> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ScheduleDO scheduleDO = new ScheduleDO();
                scheduleDO.setContent(schedulePublish.getContent());
                scheduleDO.setTime(DateTimeUtil.toUTC(schedulePublish.getTimeInMillions()));
                scheduleDO.setUserHost(userHost);
                long insert = database.scheduleDAO().insert(scheduleDO);
                if (insert > 0) {
                    callback.onComplete(new Result.Success<>(scheduleDO));
                } else {
                    callback.onComplete(new Result.Error<>(null));
                }
            }
        });
    }

}

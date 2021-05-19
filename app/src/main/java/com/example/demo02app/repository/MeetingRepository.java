package com.example.demo02app.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.demo02app.MyApplication;
import com.example.demo02app.MyExecutors;
import com.example.demo02app.R;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.db.data.MeetingDO;
import com.example.demo02app.model.meeting.data.entity.MeetingDetail;
import com.example.demo02app.model.meeting.data.entity.MeetingItem;
import com.example.demo02app.util.DateTimeUtil;
import com.example.demo02app.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MeetingRepository {
    private static final String TAG = MeetingRepository.class.getName();

    private final Application application;

    private final AppDatabase database;

    private final MyExecutors executors;

    private final String userHost;

    private MeetingRepository(Application application, AppDatabase database, MyExecutors executors) {
        this.application = application;
        this.database = database;
        this.executors = executors;
        userHost = ((MyApplication) application).getUserId();
    }

    private static MeetingRepository sInstance;

    public static MeetingRepository getInstance(Application application, AppDatabase database, MyExecutors executors) {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MeetingRepository(application, database, executors);
                }
            }
        }
        return sInstance;
    }

    // 查询本地数据库
    public LiveData<List<MeetingItem>> loadAllMeeting(String userHost) {
        Log.d(TAG, "loadAllMeeting: called");
//        loadFromNet(userHost);
        return Transformations.map(database.meetingDAO().queryAllMeetingDO(userHost), input -> {
            List<MeetingItem> meetingItems = new ArrayList<>(input.size());
            for (MeetingDO meetingDO : input) {
                MeetingItem item = new MeetingItem();
                item.setId(meetingDO.getId());
                item.setTitle(meetingDO.getTitle());
                item.setAddress(meetingDO.getAddress());
                item.setBeginTime(meetingDO.getBeginTime());
                item.setEndTime(meetingDO.getEndTime());
                meetingItems.add(item);
            }
            return meetingItems;
        });
    }

    public void loadFromNet(String userHost) {
        OkHttpUtil.post(getString(R.string.url_meeting), new HashMap<String, String>() {{
            put(getString(R.string.param_user_id), userHost);
        }}).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: called");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: called");
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    Gson gson = new Gson();
                    List<MeetingDO> meetingDOS = gson.fromJson(string, new TypeToken<List<MeetingDO>>() {
                    }.getType());

                    Log.d(TAG, "onResponse: " + meetingDOS);
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for (MeetingDO meetingDO : meetingDOS) {
                                meetingDO.setUserHost(userHost);
                            }
                            database.runInTransaction(new Runnable() {
                                @Override
                                public void run() {
                                    database.meetingDAO().deleteAll(userHost);
                                    database.meetingDAO().insert(meetingDOS);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public LiveData<MeetingDetail> loadMeetingDetail(String userHost, int id) {
        return database.meetingDAO().queryMeetingDetail(userHost, id);
    }

    public void publishMeeting(MeetingItem meetingItem, RepositoryCallback<MeetingDO> callback) {
        MeetingDO meetingDO = new MeetingDO();
        meetingDO.setTitle(meetingItem.getTitle());
        meetingDO.setAddress(meetingItem.getAddress());
        meetingDO.setUserHost(((MyApplication) application).getUserId());
        meetingDO.setBeginTime(DateTimeUtil.toUTC(meetingItem.getBeginTime()));
        meetingDO.setEndTime(DateTimeUtil.toUTC(meetingItem.getEndTime()));
        meetingDO.setPublishTime(DateTimeUtil.toUTC(DateTimeUtil.getCurrentTimeStamp()));
        meetingDO.setUserPublisher(userHost);

        Log.d(TAG, "publishMeeting: " + meetingDO.toString());

        OkHttpUtil.post(getString(R.string.url_meeting_publish), new HashMap<String, String>() {{
            put(getString(R.string.param_meeting_title), meetingDO.getTitle());
            put(getString(R.string.param_meeting_address), meetingDO.getAddress());
            put(getString(R.string.param_meeting_start_time), String.valueOf(meetingDO.getBeginTime()));
            put(getString(R.string.param_meeting_end_time), String.valueOf(meetingDO.getEndTime()));
            put(getString(R.string.param_meeting_publish_time), String.valueOf(meetingDO.getPublishTime()));
            put(getString(R.string.param_meeting_publisher), meetingDO.getUserPublisher());
        }}).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: called");
                callback.onComplete(new Result.Error<>(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: called");
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    Log.d(TAG, "onResponse: string");
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt(getString(R.string.param_result));
                        if (result == Result.RESULT_SUCCESS) {
                            MeetingDO meetingDO = new MeetingDO();
                            callback.onComplete(new Result.Success<>(meetingDO));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onComplete(new Result.Error<>(e));
                    }
                }
            }
        });
    }

//    public void addMeetingToLocal(MeetingDO meetingDO) {
//        executors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                database.meetingDAO().insert(meetingDO);
//            }
//        });
//    }

    public void delete(int id) {
        OkHttpUtil.post(getString(R.string.url_meeting_delete), new HashMap<String, String>() {{
            put(getString(R.string.param_user_id), userHost);
            put(getString(R.string.param_meeting_id), String.valueOf(id));
        }}).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: called");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse delete: called");
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt(getString(R.string.param_result));
                        if (result == Result.RESULT_SUCCESS) {
                            loadFromNet(userHost);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getString(@StringRes int strId) {
        return application.getApplicationContext().getString(strId);
    }
}

package com.example.demo02app.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;

import com.example.demo02app.MyApplication;
import com.example.demo02app.MyExecutors;
import com.example.demo02app.R;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.db.data.NoticeDO;
import com.example.demo02app.model.notice.data.model.NoticeItem;
import com.example.demo02app.model.notice.data.model.NoticePublish;
import com.example.demo02app.util.DateTimeUtil;
import com.example.demo02app.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NoticeRepository {
    private static final String TAG = NoticeRepository.class.getName();
    @NonNull
    private final Application application;

    @NonNull
    private final AppDatabase database;

    @NonNull
    private final MyExecutors executors;

    @NonNull
    private final String userHost;


    private NoticeRepository(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        this.application = application;
        this.executors = executors;
        this.database = database;
        userHost = ((MyApplication) application).getUserId();
    }

    private static NoticeRepository sInstance;

    public static NoticeRepository getInstance(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (NoticeRepository.class) {
                if (sInstance == null) {
                    sInstance = new NoticeRepository(application, database, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<NoticeItem>> loadAllNoticeItem() {
        loadFromNet();
        return database.noticeDAO().queryAllNoticeItem(userHost);
    }

    public void loadFromNet() {
        OkHttpUtil.post(getString(R.string.url_notice), new HashMap<String, String>() {{
            put(getString(R.string.param_user_id), userHost);
        }}).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: called");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();

                    Gson gson = new Gson();
                    TypeToken<List<NoticeDO>> typeToken = new TypeToken<List<NoticeDO>>() {
                    };
                    List<NoticeDO> noticeDOS = gson.fromJson(string, typeToken.getType());
                    Log.d(TAG, "onResponse:  " + string);
                    Log.d(TAG, "onResponse:  " + noticeDOS);
                    executors.diskIO().execute(() -> {
                        for (NoticeDO noticeDO : noticeDOS) {
                            noticeDO.setUserHost(userHost);
                        }
                        database.noticeDAO().deleteAll(userHost);
                        database.noticeDAO().insert(noticeDOS);
                    });
                }
            }
        });
    }

    public void publish(NoticePublish noticePublish, RepositoryCallback<NoticeDO> callback) {
        NoticeDO noticeDO = new NoticeDO();
        noticeDO.setTitle(noticePublish.getTitle());
        noticeDO.setContent(noticePublish.getContent());
        noticeDO.setPublisher(userHost);
        noticeDO.setPublishTime(DateTimeUtil.toUTC(DateTimeUtil.getCurrentTimeStamp()));
        OkHttpUtil.post(getString(R.string.url_notice_publish), new HashMap<String, String>() {{
            put(getString(R.string.param_notice_title), noticeDO.getTitle());
            put(getString(R.string.param_notice_content), noticeDO.getContent());
            put(getString(R.string.param_notice_publisher), noticeDO.getPublisher());
            put(getString(R.string.param_notice_publish_time), String.valueOf(noticeDO.getPublishTime()));
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
                    Log.d(TAG, "onResponse: " + string);
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt(getString(R.string.param_result));
                        if (result == Result.RESULT_SUCCESS) {
                            callback.onComplete(new Result.Success<>(null));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onComplete(new Result.Error<>(e));
                    }
                }
            }
        });
    }

    public void delete(int id, RepositoryCallback<Integer> callback) {
        OkHttpUtil.post(getString(R.string.url_notice_delete), new HashMap<String, String>() {{
            put(getString(R.string.param_user_id), userHost);
            put(getString(R.string.param_notice_id), String.valueOf(id));
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
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt(getString(R.string.param_result));
                        if (result == Result.RESULT_SUCCESS) {
                            callback.onComplete(new Result.Success<>(id));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onComplete(new Result.Error<>(e));
                    }
                } else {
                    callback.onComplete(new Result.Error<>(new IOException()));
                }
            }
        });
    }

    private String getString(@StringRes int resId) {
        return application.getApplicationContext().getString(resId);
    }

}

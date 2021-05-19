package com.example.demo02app.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.AppDatabase;

public class LogRepository {
    @NonNull
    private final Application application;
    @NonNull
    private final AppDatabase database;
    @NonNull
    private final MyExecutors executors;

    private LogRepository(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        this.application = application;
        this.database = database;
        this.executors = executors;
    }

    private static LogRepository sInstance;

    public static LogRepository getInstance(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (LogRepository.class) {
                if (sInstance == null) {
                    sInstance = new LogRepository(application,database,executors);
                }
            }
        }
        return sInstance;
    }

}

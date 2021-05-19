package com.example.demo02app.model.log.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.repository.LogRepository;

public class LogListViewModel extends AndroidViewModel {

    @NonNull
    private final LogRepository logRepository;

    public LogListViewModel(@NonNull Application application, @NonNull LogRepository logRepository) {
        super(application);
        this.logRepository = logRepository;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final LogRepository LogRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.LogRepository = ((MyApplication) application).getLogRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(LogListViewModel.class)) {
                return (T) new LogListViewModel(application, LogRepository);
            }
            throw new IllegalArgumentException();
        }
    }
}
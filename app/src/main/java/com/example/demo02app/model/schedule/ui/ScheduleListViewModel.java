package com.example.demo02app.model.schedule.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.schedule.data.ScheduleItem;
import com.example.demo02app.repository.ScheduleRepository;

import java.util.List;


public class ScheduleListViewModel extends AndroidViewModel {

    @NonNull
    private final ScheduleRepository scheduleRepository;

    private final LiveData<List<ScheduleItem>> scheduleItemListLiveData;

    public ScheduleListViewModel(@NonNull Application application,
                                 @NonNull ScheduleRepository scheduleRepository) {
        super(application);
        this.scheduleRepository = scheduleRepository;
        scheduleItemListLiveData = scheduleRepository.loadScheduleItem();
    }

    public LiveData<List<ScheduleItem>> getScheduleItemListLiveData() {
        return scheduleItemListLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final ScheduleRepository scheduleRepository;


        public Factory(@NonNull Application application) {
            this.application = application;
            this.scheduleRepository = ((MyApplication) application).getScheduleRepository();

        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ScheduleListViewModel.class)) {
                return (T) new ScheduleListViewModel(application, scheduleRepository);
            }
            throw new IllegalArgumentException();
        }
    }
}
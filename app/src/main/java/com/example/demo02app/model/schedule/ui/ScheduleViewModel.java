package com.example.demo02app.model.schedule.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.db.data.ScheduleDO;
import com.example.demo02app.model.schedule.data.SchedulePublish;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;
import com.example.demo02app.repository.ScheduleRepository;
import com.example.demo02app.util.DateTimeUtil;

public class ScheduleViewModel extends AndroidViewModel {

    private static final String TAG = ScheduleViewModel.class.getName();
    @NonNull
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<String> timeLiveData;

    private final LiveData<SchedulePublish> schedulePublishLiveData;

    public ScheduleViewModel(@NonNull Application application, @NonNull ScheduleRepository scheduleRepository, long timeInMillions) {
        super(application);
        this.scheduleRepository = scheduleRepository;
        this.timeLiveData = new MutableLiveData<>();
        this.timeLiveData.setValue(DateTimeUtil.getDateString(timeInMillions));
        schedulePublishLiveData = scheduleRepository.loadScheduleByTime(timeInMillions);
    }

    public LiveData<SchedulePublish> getSchedulePublishLiveData() {
        return schedulePublishLiveData;
    }

    public void save() {
        SchedulePublish value = schedulePublishLiveData.getValue();
        Log.d(TAG, "save: called");
        Log.d(TAG, "save: Schedule" + value);
        if (value != null && value.getContent() != null) {
            Log.d(TAG, "save: ");
            scheduleRepository.addSchedule(value, new RepositoryCallback<ScheduleDO>() {
                @Override
                public void onComplete(Result<ScheduleDO> t) {
                    if (t instanceof Result.Success) {
                        Log.d(TAG, "onComplete: success");
                    } else {
                        Log.d(TAG, "onComplete: fail");
                    }
                }
            });
        } else {
            Log.d(TAG, "save: failed");
        }
    }

    public LiveData<String> getTimeLiveData() {
        return timeLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final ScheduleRepository scheduleRepository;

        private final long timeInMillions;

        public Factory(@NonNull Application application, long timeInMillions) {
            this.application = application;
            this.scheduleRepository = ((MyApplication) application).getScheduleRepository();
            this.timeInMillions = timeInMillions;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ScheduleViewModel.class)) {
                return (T) new ScheduleViewModel(application, scheduleRepository, timeInMillions);
            }
            throw new IllegalArgumentException();
        }
    }
}
package com.example.demo02app.model.meeting.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.meeting.data.entity.MeetingItem;
import com.example.demo02app.model.message.ui.MessageListViewModel;
import com.example.demo02app.repository.MeetingRepository;

import java.util.List;

public class MeetingListViewModel extends AndroidViewModel {
    private static final String TAG = MessageListViewModel.class.getName();
    @NonNull
    private final MeetingRepository meetingRepository;

    private final LiveData<List<MeetingItem>> meetingItemLiveData;

    private final String userHost;

    public MeetingListViewModel(@NonNull Application application, @NonNull MeetingRepository meetingRepository) {
        super(application);
        Log.d(TAG, "MeetingListViewModel: called");
        this.meetingRepository = meetingRepository;
        userHost = ((MyApplication) application).getUserId();
        meetingItemLiveData = meetingRepository.loadAllMeeting(userHost);
    }

    public LiveData<List<MeetingItem>> getMeetingItemLiveData() {
        return meetingItemLiveData;
    }

    public void reLoad() {
        meetingRepository.loadAllMeeting(userHost);
    }

    public void delete(int id) {
        meetingRepository.delete(id);
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final MeetingRepository meetingRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.meetingRepository = ((MyApplication) application).getMeetingRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MeetingListViewModel(application, meetingRepository);
        }
    }

}
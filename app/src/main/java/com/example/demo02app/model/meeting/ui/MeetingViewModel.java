package com.example.demo02app.model.meeting.ui;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.meeting.data.entity.MeetingDetail;
import com.example.demo02app.model.meeting.data.entity.MeetingDetailView;
import com.example.demo02app.repository.MeetingRepository;
import com.example.demo02app.util.DateTimeUtil;

public class MeetingViewModel extends AndroidViewModel {
    private static final String TAG = MeetingViewModel.class.getName();
    @NonNull
    private final MeetingRepository meetingRepository;

    private int id;

    private LiveData<MeetingDetail> meetingDetailLiveData;

    private LiveData<MeetingDetailView> meetingDetailViewLiveData;

    public MeetingViewModel(@NonNull Application application, @NonNull MeetingRepository meetingRepository, int id) {
        super(application);
        this.meetingRepository = ((MyApplication) application).getMeetingRepository();
        String userHost = ((MyApplication) application).getUserId();
        meetingDetailLiveData = meetingRepository.loadMeetingDetail(userHost, id);
        meetingDetailViewLiveData = Transformations.map(meetingDetailLiveData, new Function<MeetingDetail, MeetingDetailView>() {
            @Override
            public MeetingDetailView apply(MeetingDetail input) {
                Log.d(TAG, "apply: input " + input.toString());
                MeetingDetailView meetingDetailView = new MeetingDetailView();
                meetingDetailView.setTitle(TextUtils.isEmpty(input.getTitle()) ? "暂无数据" : input.getTitle());
                meetingDetailView.setAddress(TextUtils.isEmpty(input.getAddress()) ? "暂无数据" : input.getAddress());
                meetingDetailView.setPublisher(TextUtils.isEmpty(input.getPublisher()) ? "暂无数据" : input.getPublisher());
                meetingDetailView.setBeginTime(input.getBeginTime() == 0 ? "" : DateTimeUtil.getDateAndTimeStringUTC(input.getBeginTime()));
                meetingDetailView.setEndTime(input.getEndTime() == 0 ? "" : DateTimeUtil.getDateAndTimeStringUTC(input.getEndTime()));
                meetingDetailView.setPublishTime(input.getPublishTime() == 0 ? "" : DateTimeUtil.getDateAndTimeStringUTC(input.getPublishTime()));
                return meetingDetailView;
            }
        });
    }

    public LiveData<MeetingDetail> getMeetingDetailLiveData() {
        return meetingDetailLiveData;
    }

    public LiveData<MeetingDetailView> getMeetingDetailViewLiveData() {
        return meetingDetailViewLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        @NonNull
        private final MeetingRepository meetingRepository;

        private final int id;

        public Factory(@NonNull Application application, int id) {
            this.application = application;
            this.meetingRepository = ((MyApplication) application).getMeetingRepository();
            this.id = id;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
                return (T) new MeetingViewModel(application, meetingRepository, id);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
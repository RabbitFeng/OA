package com.example.demo02app.model.meeting.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.meeting.data.model.MeetingItem;
import com.example.demo02app.repository.MeetingRepository;

public class MeetingPublishViewModel extends AndroidViewModel {
    private MeetingRepository meetingRepository;

    private MutableLiveData<MeetingItem> meetingDOMutableLiveData;

    public MeetingPublishViewModel(@NonNull Application application) {
        super(application);
        meetingRepository = ((MyApplication) application).getMeetingRepository();
        meetingDOMutableLiveData = new MutableLiveData<>();
        meetingDOMutableLiveData.setValue(new MeetingItem());
    }

    public void publish(){

    }

    public MutableLiveData<MeetingItem> getMeetingDOMutableLiveData() {
        return meetingDOMutableLiveData;
    }
}
package com.example.demo02app.model.meeting.ui;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.db.data.MeetingDO;
import com.example.demo02app.model.meeting.data.MeetingFormState;
import com.example.demo02app.model.meeting.data.MeetingPublishResult;
import com.example.demo02app.model.meeting.data.model.MeetingItem;
import com.example.demo02app.model.meeting.data.model.MeetingItemView;
import com.example.demo02app.repository.MeetingRepository;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;
import com.example.demo02app.util.DateTimeUtil;

public class MeetingPublishViewModel extends AndroidViewModel {
    private static final String TAG = MeetingPublishViewModel.class.getName();
    private final MeetingRepository meetingRepository;
    private final MutableLiveData<MeetingItem> meetingItemLiveData;
    private final LiveData<MeetingItemView> meetingItemViewLiveData;

    private final MutableLiveData<MeetingFormState> meetingFormStateLiveData;

    private final MutableLiveData<MeetingPublishResult> publishResultLiveData;


    //    private MutableLiveData<>
    public MeetingPublishViewModel(@NonNull Application application) {
        super(application);
        meetingRepository = ((MyApplication) application).getMeetingRepository();
        meetingItemLiveData = new MutableLiveData<>();
        meetingItemLiveData.setValue(new MeetingItem());

        meetingItemViewLiveData = Transformations.map(meetingItemLiveData, new Function<MeetingItem, MeetingItemView>() {
            @Override
            public MeetingItemView apply(MeetingItem input) {
                MeetingItemView itemView = new MeetingItemView();
                itemView.setTitle(TextUtils.isEmpty(input.getTitle()) ? "暂无数据" : input.getTitle());
                itemView.setAddress(TextUtils.isEmpty(input.getAddress()) ? "暂无数据" : input.getAddress());
                itemView.setBeginTime(input.getBeginTime() == 0 ? "暂无数据" : DateTimeUtil.getDateAndTimeStringUTC(input.getBeginTime()));
                itemView.setEndTime(input.getEndTime() == 0 ? "暂无数据" : DateTimeUtil.getDateAndTimeStringUTC(input.getEndTime()));
                return itemView;
            }
        });

        meetingFormStateLiveData = new MutableLiveData<>();
        publishResultLiveData = new MutableLiveData<>();
    }

    public void publish() {
//        Log.d(TAG, "publish: " + Objects.requireNonNull(meetingItemLiveData.getValue()).toString());
        Log.d(TAG, "publish: called");
        MeetingItem meetingItem = getMeetingItemLiveData().getValue();
        if (meetingItem == null) {
            return;
        }

        Log.d(TAG, "publish: " + meetingItem.toString());
        meetingRepository.publishMeeting(meetingItem, new RepositoryCallback<MeetingDO>() {
            @Override
            public void onComplete(Result<MeetingDO> t) {
                Log.d(TAG, "onComplete: called");
                if (t instanceof Result.Success) {
                    Log.d(TAG, "onComplete: called success");
                    publishResultLiveData.postValue(new MeetingPublishResult(null));
                } else {
                    Log.d(TAG, "onComplete: called error");
                    publishResultLiveData.postValue(new MeetingPublishResult(R.string.ui_publish_failed));
                }
            }
        });
    }

    public void updateBeginTime(long timeStamp) {
        MeetingItem value = meetingItemLiveData.getValue();
        if (value != null) {
            value.setBeginTime(timeStamp);
        }
        meetingItemLiveData.setValue(value);
        updateFormState();
    }

    public void updateEndTime(long timeStamp) {
        MeetingItem value = meetingItemLiveData.getValue();
        if (value != null) {
            value.setEndTime(timeStamp);
        }
        meetingItemLiveData.setValue(value);
        updateFormState();
    }

    public void updateFormState() {
        MeetingItem meetingItem = meetingItemLiveData.getValue();
        if (meetingItem == null) {
            meetingFormStateLiveData.setValue(new MeetingFormState(false));
            return;
        }
        if (TextUtils.isEmpty(meetingItem.getTitle())) {
            meetingFormStateLiveData.setValue(new MeetingFormState(R.string.ui_title_invalid));
            return;
        }
        if (TextUtils.isEmpty(meetingItem.getAddress())) {
            meetingFormStateLiveData.setValue(new MeetingFormState(R.string.ui_address_invalid));
            return;
        }
        if (meetingItem.getBeginTime() == 0) {
            meetingFormStateLiveData.setValue(new MeetingFormState(R.string.ui_start_time_invalid));
            return;
        }
        if (meetingItem.getEndTime() == 0) {
            meetingFormStateLiveData.setValue(new MeetingFormState(R.string.ui_end_time_invalid));
            return;
        }
        if (meetingItem.getEndTime() < meetingItem.getBeginTime()) {
            meetingFormStateLiveData.setValue(new MeetingFormState(R.string.ui_start_after_end_time));
            return;
        }
        meetingFormStateLiveData.setValue(new MeetingFormState(true));

    }

    public MutableLiveData<MeetingItem> getMeetingItemLiveData() {
        return meetingItemLiveData;
    }

    public LiveData<MeetingItemView> getMeetingItemViewLiveData() {
        return meetingItemViewLiveData;
    }

    public LiveData<MeetingFormState> getMeetingFormStateLiveData() {
        return meetingFormStateLiveData;
    }

    public LiveData<MeetingPublishResult> getPublishResultLiveData() {
        return publishResultLiveData;
    }
}
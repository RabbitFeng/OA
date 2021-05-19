package com.example.demo02app.model.notice.ui;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.db.data.NoticeDO;
import com.example.demo02app.model.notice.NoticePublishResult;
import com.example.demo02app.model.notice.data.NoticePublishFormState;
import com.example.demo02app.model.notice.data.model.NoticePublish;
import com.example.demo02app.repository.NoticeRepository;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;

public class NoticePublishViewModel extends AndroidViewModel {
    private static final String TAG = NoticePublishViewModel.class.getName();
    private final NoticeRepository noticeRepository;

    private MutableLiveData<NoticePublish> noticePublishLiveData;
    private MutableLiveData<NoticePublishFormState> formStateLiveData;
    private MutableLiveData<NoticePublishResult> resultLiveData;

    public NoticePublishViewModel(@NonNull Application application, NoticeRepository noticeRepository) {
        super(application);
        this.noticeRepository = noticeRepository;
        noticePublishLiveData = new MutableLiveData<>();
        noticePublishLiveData.setValue(new NoticePublish());
        formStateLiveData = new MutableLiveData<>();
        resultLiveData = new MutableLiveData<>();
    }

    public void updateFormState() {
        NoticePublish value = noticePublishLiveData.getValue();
        if (value == null) {
            formStateLiveData.setValue(new NoticePublishFormState(false));
        } else if (TextUtils.isEmpty(value.getTitle())) {
            formStateLiveData.setValue(new NoticePublishFormState(R.string.ui_title_invalid));
        } else if (TextUtils.isEmpty(value.getTitle())) {
            formStateLiveData.setValue(new NoticePublishFormState(R.string.ui_content_invalid));
        } else {
            formStateLiveData.setValue(new NoticePublishFormState(true));
        }
    }

    public void publish() {
        NoticePublish value = noticePublishLiveData.getValue();
        if (value == null) {
            resultLiveData.setValue(new NoticePublishResult(R.string.ui_publish_failed));
            return;
        }
        noticeRepository.publish(value, new RepositoryCallback<NoticeDO>() {
            @Override
            public void onComplete(Result<NoticeDO> t) {
                if (t instanceof Result.Success) {
                    resultLiveData.postValue(new NoticePublishResult(null));
                } else {
                    resultLiveData.postValue(new NoticePublishResult(R.string.ui_publish_failed));
                }
            }
        });
    }

    public LiveData<NoticePublish> getNoticePublishLiveData() {
        return noticePublishLiveData;
    }

    public LiveData<NoticePublishFormState> getFormStateLiveData() {
        return formStateLiveData;
    }

    public LiveData<NoticePublishResult> getResultLiveData() {
        return resultLiveData;
    }


    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final NoticeRepository noticeRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.noticeRepository = ((MyApplication) application).getNoticeRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(NoticePublishViewModel.class)) {
                return (T) new NoticePublishViewModel(application, noticeRepository);
            }
            throw new IllegalArgumentException();
        }
    }

}
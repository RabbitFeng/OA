package com.example.demo02app.model.notice.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.notice.data.model.NoticeItem;
import com.example.demo02app.repository.NoticeRepository;

public class NoticePublishViewModel extends AndroidViewModel {
    private static final String TAG = NoticePublishViewModel.class.getName();
    private final NoticeRepository noticeRepository;

    private MutableLiveData<NoticeItem> noticeItemLiveData;

    public NoticePublishViewModel(@NonNull Application application, NoticeRepository noticeRepository) {
        super(application);
        this.noticeRepository = noticeRepository;
        noticeItemLiveData = new MutableLiveData<>();
        noticeItemLiveData.setValue(new NoticeItem());


    }

    public MutableLiveData<NoticeItem> getNoticeItemLiveData() {
        return noticeItemLiveData;
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
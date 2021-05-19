package com.example.demo02app.model.notice.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.notice.data.model.NoticeItem;
import com.example.demo02app.repository.NoticeRepository;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;

import java.util.List;

public class NoticeListViewModel extends AndroidViewModel {
    @NonNull
    private final NoticeRepository repository;

    private final LiveData<List<NoticeItem>> noticeListLiveData;

    public NoticeListViewModel(@NonNull Application application, @NonNull NoticeRepository repository) {
        super(application);
        this.repository = repository;
        noticeListLiveData = repository.loadAllNoticeItem();
    }

    public LiveData<List<NoticeItem>> getNoticeListLiveData() {
        return noticeListLiveData;
    }

    public void reload() {
        repository.loadFromNet();
    }

    public void delete(int id) {
        repository.delete(id, new RepositoryCallback<Integer>() {
            @Override
            public void onComplete(Result<Integer> t) {
                if (t instanceof Result.Success) {
                    repository.loadFromNet();
                }
            }
        });
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        @NonNull
        private final NoticeRepository noticeRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            noticeRepository = ((MyApplication) application).getNoticeRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(NoticeListViewModel.class)) {
                return (T) new NoticeListViewModel(application, noticeRepository);
            }
            throw new IllegalArgumentException();
        }
    }
}
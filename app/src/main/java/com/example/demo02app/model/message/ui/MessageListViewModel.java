package com.example.demo02app.model.message.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.message.data.MessageItem;
import com.example.demo02app.repository.MessageRepository;

import java.util.List;

public class MessageListViewModel extends AndroidViewModel {

    @NonNull
    private MessageRepository messageRepository;

    private LiveData<List<MessageItem>> messageItemListLiveData;

    public MessageListViewModel(@NonNull Application application, @NonNull MessageRepository messageRepository) {
        super(application);
        this.messageRepository = messageRepository;
        messageItemListLiveData = messageRepository.getMessageItemLiveData(((MyApplication) application).getUserId());
    }

    public LiveData<List<MessageItem>> getMessageItemListLiveData() {
        return messageItemListLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final MessageRepository messageRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.messageRepository = ((MyApplication)application).getMessageRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MessageListViewModel(application, messageRepository);
        }
    }
}
package com.example.demo02app.model.chat.ui;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.chat.entity.ChatMessage;
import com.example.demo02app.model.chat.entity.ChatMessageItem;
import com.example.demo02app.repository.MessageRepository;
import com.example.demo02app.util.DateTimeUtil;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private static final String TAG = ChatViewModel.class.getName();
    private String userHost;
    private String userOther;

    private final MutableLiveData<ChatMessage> chatMessageMutableLiveData;

    private final LiveData<List<ChatMessageItem>> chatMessageListLiveData;

    private MessageRepository messageRepository;

    public ChatViewModel(@NonNull Application application, String userOther, MessageRepository messageRepository) {
        super(application);
        this.userHost = ((MyApplication) application).getUserId();
        Log.d(TAG, "ChatViewModel: userHost:" + userHost);
        this.userOther = userOther;
        this.messageRepository = messageRepository;
        chatMessageMutableLiveData = new MutableLiveData<>();
        chatMessageListLiveData = messageRepository.loadChatMessageListLiveData(userHost, userOther);
    }

    public void update(String content) {
        Log.d(TAG, "update: called");
        chatMessageMutableLiveData.setValue(new ChatMessage(userHost, userOther, content, DateTimeUtil.getCurrentTimeStamp()));
    }

    public String getUserOther() {
        return userOther;
    }

    public LiveData<ChatMessage> getChatMessageMutableLiveData() {
        return chatMessageMutableLiveData;
    }

    public LiveData<List<ChatMessageItem>> getChatMessageListLiveData() {
        return chatMessageListLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final String userOther;
        private final Application application;

        private final MessageRepository messageRepository;

        public Factory(Application application, String userOther) {
            this.application = application;
            this.userOther = userOther;
            this.messageRepository = ((MyApplication) application).getMessageRepository();
        }

        @NonNull
        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ChatViewModel.class)) {
                return (T) new ChatViewModel(application, userOther, messageRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
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
import com.example.demo02app.db.data.AddressBookDO;
import com.example.demo02app.model.chat.data.ChatMessage;
import com.example.demo02app.model.chat.data.ChatMessageItem;
import com.example.demo02app.repository.AddressBookRepository;
import com.example.demo02app.repository.MessageRepository;
import com.example.demo02app.util.DateTimeUtil;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private static final String TAG = ChatViewModel.class.getName();
    private final String userHost;
    private final String userOther;

    private final MutableLiveData<ChatMessage> chatMessageMutableLiveData;

    private final LiveData<List<ChatMessageItem>> chatMessageListLiveData;

    private final LiveData<AddressBookDO> userOtherAddressBookLiveData;

    private final MessageRepository messageRepository;

    private final AddressBookRepository addressBookRepository;

    public ChatViewModel(@NonNull Application application, String userOther,
                         MessageRepository messageRepository,
                         AddressBookRepository addressBookRepository) {
        super(application);
        this.userHost = ((MyApplication) application).getUserId();
        this.userOther = userOther;
        Log.d(TAG, "ChatViewModel: userHost:" + userHost);
        this.messageRepository = messageRepository;
        this.addressBookRepository = addressBookRepository;
        chatMessageMutableLiveData = new MutableLiveData<>();
        chatMessageListLiveData = messageRepository.loadChatMessageListLiveData(userHost, userOther);
        userOtherAddressBookLiveData = addressBookRepository.findAddressBookByUserId(userHost, userOther);
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

    public LiveData<AddressBookDO> getUserOtherAddressBookLiveData() {
        return userOtherAddressBookLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final String userOther;
        private final Application application;

        private final MessageRepository messageRepository;
        private final AddressBookRepository addressBookRepository;

        public Factory(Application application, String userOther) {
            this.application = application;
            this.userOther = userOther;
            this.messageRepository = ((MyApplication) application).getMessageRepository();
            this.addressBookRepository = ((MyApplication) application).getAddressBookRepository();
        }

        @NonNull
        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ChatViewModel.class)) {
                return (T) new ChatViewModel(application, userOther, messageRepository, addressBookRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
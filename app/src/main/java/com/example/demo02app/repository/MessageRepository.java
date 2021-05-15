package com.example.demo02app.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.db.data.ChatListDO;
import com.example.demo02app.db.data.ChatMessageDO;
import com.example.demo02app.model.chat.entity.ChatMessage;
import com.example.demo02app.model.chat.entity.ChatMessageItem;
import com.example.demo02app.model.message.entity.MessageItem;

import java.util.List;

public class MessageRepository {
    private static final String TAG = "MessageRepository";

    @NonNull
    private final Application application;
    @NonNull
    private final AppDatabase database;
    @NonNull
    private final MyExecutors executors;

    public MessageRepository(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        this.application = application;
        this.database = database;
        this.executors = executors;
    }

    private static MessageRepository sInstance;

    public static MessageRepository getInstance(@NonNull Application application, @NonNull AppDatabase database, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (MessageRepository.class) {
                if (sInstance == null) {
                    sInstance = new MessageRepository(application, database, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<MessageItem>> getMessageItemLiveData(String userHost) {
//        return Transformations.map(database.chatMessageDao().queryAllChatList(userHost), new Function<List<ChatListDO>, List<MessageItem>>() {
//            @Override
//            public List<MessageItem> apply(List<ChatListDO> chatListDOS) {
//                List<MessageItem> messageItems = new ArrayList<>(chatListDOS.size());
//                for (ChatListDO chatListDO : chatListDOS) {
//                    MessageItem messageItem = new MessageItem();
//                    messageItem.setUserHost(chatListDO.getUserHost());
//                    messageItem.setUserOther(chatListDO.getUserOther());
//                    messageItem.setIsSend(chatListDO.getIsSend());
//                    messageItem.setContent(chatListDO.getContent());
//                    messageItem.setTimeOfLatestMessage(chatListDO.getTimeOfLatestMessage());
//                    messageItems.add(messageItem);
//                }
//                return messageItems;
//            }
//        });
        return database.chatMessageDao().queryMessageItem(userHost);
    }

    public LiveData<List<ChatMessageItem>> loadChatMessageListLiveData(String userHost, String userOther) {
//        return Transformations.map(database.chatMessageDao().queryAllChatMessage(userHost, userOther), chatMessageDOS -> {
//            List<ChatMessage> chatMessages = new ArrayList<>(chatMessageDOS.size());
//            for (ChatMessageDO chatMessageDO : chatMessageDOS) {
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setUserSender(chatMessageDO.isSend() ? chatMessageDO.getUserHost() : chatMessageDO.getUserOther());
//                chatMessage.setUserReceiver(chatMessage.isSend() ? chatMessageDO.getUserOther() : chatMessageDO.getUserHost());
//                chatMessage.setSend(chatMessage.isSend());
//                chatMessage.setContent(chatMessageDO.getContent());
//                chatMessage.setTimestamp(chatMessageDO.getTime());
//                chatMessages.add(chatMessage);
//            }
//            return chatMessages;
//        });
        return database.chatMessageDao().queryChatMessage(userHost, userOther);
    }

    @MainThread
    public void handleChatMessage(ChatMessage chatMessage, boolean isSend) {
        Log.d(TAG, "insertMessage: called");
        executors.diskIO().execute(() -> {
            ChatMessageDO chatMessageDO = new ChatMessageDO();
            chatMessageDO.setUserHost(isSend ? chatMessage.getUserSender() : chatMessage.getUserReceiver());
            chatMessageDO.setUserOther(isSend ? chatMessage.getUserReceiver() : chatMessage.getUserSender());
            chatMessageDO.setSend(isSend);
            chatMessageDO.setContent(chatMessage.getContent());
            chatMessageDO.setTime(chatMessage.getTimestamp());
            database.chatMessageDao().insertChatMessage(chatMessageDO);
            ChatListDO chatListDO = new ChatListDO();
            chatListDO.setContent(chatMessageDO.getContent());
            chatListDO.setTimeOfLatestMessage(chatMessageDO.getTime());
            chatListDO.setUserHost(chatMessageDO.getUserHost());
            chatListDO.setUserOther(chatMessageDO.getUserOther());
            chatListDO.setSend(chatMessageDO.isSend());
            handleMessageItem(chatListDO);
        });
    }

    @WorkerThread
    public void handleMessageItem(ChatListDO chatListDO) {
        database.chatMessageDao().insertChatList(chatListDO);
    }
}

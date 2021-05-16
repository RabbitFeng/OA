package com.example.demo02app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.model.chat.model.ChatMessage;
import com.example.demo02app.repository.MessageRepository;
import com.example.demo02app.util.websocket.JWebSocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClientService extends Service {
    private static final String TAG = JWebSocketClientService.class.getName();

    private final IBinder binder = new JWebSocketClientBinder();
    private URI uri;
    private JWebSocketClient client;

    private MessageRepository messageRepository;

    // Binder
    public class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            Log.d(TAG, "getService: called");
            return JWebSocketClientService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
        messageRepository = ((MyApplication) getApplication()).getMessageRepository();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String u_id = intent.getStringExtra(getString(R.string.param_user_id));
        Log.d(TAG, "onStartCommand: called " + u_id);
        client = new JWebSocketClient(URI.create(getString(R.string.url_webSocket) + "/" + u_id)) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                // 收到消息
                Log.d(TAG, "onMessage: called:" + message);
                Gson gson = new GsonBuilder().create();
                ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
                Log.d(TAG, "onMessage: " + chatMessage);

                messageRepository.handleChatMessage(chatMessage, false);
//                sendBroadcast();
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                Log.d(TAG, "onOpen: called");
                super.onOpen(handshake);
            }
        };
        // 自动开启连接
        new Thread(() -> {
            try {
                client.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: called");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: called");
        super.onDestroy();
    }

    public void sendMessage(ChatMessage message) {
        if (client != null && client.isOpen()) {
            String s = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(message);
            Log.d(TAG, "sendMessage: " + s);
            messageRepository.handleChatMessage(message, true);
            client.send(s);
        }
    }

    private void sendBroadcast() {
        Log.d(TAG, "sendBroadcast: called");
        Intent intent = new Intent();
        intent.setAction(getString(R.string.action_webSocket));
//        sendBroadcast(intent);
    }
}
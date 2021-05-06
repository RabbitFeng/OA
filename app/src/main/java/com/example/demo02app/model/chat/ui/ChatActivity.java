package com.example.demo02app.model.chat.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo02app.R;
import com.example.demo02app.service.JWebSocketClientService;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getName();
    private JWebSocketClientService socketClientService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof JWebSocketClientService.JWebSocketClientBinder) {
                socketClientService = ((JWebSocketClientService.JWebSocketClientBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            socketClientService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        Intent intent = getIntent();
//        String messageFrom = intent.getStringExtra(getString(R.string.bundle_message_from));
//        Log.d(TAG, "onCreate: MessageFrom:" + messageFrom);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, ChatFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: BindService");
        // 绑定服务
        Intent bindService = new Intent(ChatActivity.this, JWebSocketClientService.class);
        bindService(bindService, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
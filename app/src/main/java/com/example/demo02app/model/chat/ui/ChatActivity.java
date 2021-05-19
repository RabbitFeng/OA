package com.example.demo02app.model.chat.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.model.chat.data.ChatMessage;
import com.example.demo02app.service.JWebSocketClientService;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getName();
    private JWebSocketClientService socketClientService;
    private ChatViewModel viewModel;

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


        String userOther = getIntent().getBundleExtra(getString(R.string.bundle_name))
                .getString(getString(R.string.bundle_user_other));
        ChatViewModel.Factory factory = new ChatViewModel.Factory(getApplication(), userOther);
        viewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);

        Log.d(TAG, "onCreate: userOther:" + userOther);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new ChatFragment())
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

    public void sendMessage(ChatMessage chatMessage) {
        socketClientService.sendMessage(chatMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        return super.onCreateOptionsMenu(menu);
    }
}
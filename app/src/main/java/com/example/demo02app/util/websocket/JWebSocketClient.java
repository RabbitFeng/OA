package com.example.demo02app.util.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

// 只能创建一次
public class JWebSocketClient extends WebSocketClient {

    private static final String TAG = JWebSocketClient.class.getName();

    public JWebSocketClient(URI serverUri) {
        super(serverUri);
    }

//    private static JWebSocketClient jWebSocketClient;

    // 获取单例
//    public static JWebSocketClient getInstance(URI serverUri) {
//        synchronized (JWebSocketClient.class) {
//            if (jWebSocketClient == null) {
//                synchronized (JWebSocketClient.class) {
//                    jWebSocketClient = new JWebSocketClient(serverUri);
//                }
//            }
//        }
//        return jWebSocketClient;
//    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: called");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: called");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: called");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: called");
    }

}



package com.example.demo02app.model.chat.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo02app.R;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container,ChatFragment.newInstance())
                    .commitNow();
        }
    }
}
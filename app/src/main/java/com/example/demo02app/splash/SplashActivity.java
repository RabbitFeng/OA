package com.example.demo02app.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.login.data.LoginResult;
import com.example.demo02app.login.ui.LoginActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getName();
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash);

        SplashViewModel.Factory factory = new SplashViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(SplashViewModel.class);

        // observe isLogoutLiveData. When isLogout is not null, userCache is loaded.
        viewModel.getIsLogoutLiveData().observe(this, isLogout -> {
            if(isLogout==null){
                return;
            }
            if(isLogout){
                Log.d(TAG, "isLogout: 用户注销，跳转到登录界面");
                intentToLogin();
            }else {
                Log.d(TAG, "isLogout: 用户未注销，自动登录");
                viewModel.autoLogin();
            }
        });

        // observe the result of login
        viewModel.getLoginResultLiveData().observe(this, loginResult -> {
            Log.d(TAG, "loginResult: called");
            if (loginResult == null || loginResult.getResult() == null) {
                return;
            }
            Log.d(TAG, "loginResult:" + loginResult.getResult());
            if (loginResult.getResult() == LoginResult.SUCCESS) {
                // login succeed
                intentToMain();
            } else {
                // login fail
                intentToLogin();
            }
        });
    }

    private void intentToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void intentToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
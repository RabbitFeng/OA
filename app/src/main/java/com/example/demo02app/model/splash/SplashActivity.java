package com.example.demo02app.model.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.model.login.ui.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getName();
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModel.Factory factory = new SplashViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(SplashViewModel.class);

        // observe isLogoutLiveData. When isLogout is not null, userCache is loaded.
        viewModel.getLoggedInUserLiveData().observe(this, user -> {
            if (user == null) {
                return;
            }
            if (user.isLogout()) {
                // 用户注销，则跳转到登录界面
                intentToLogin();
            } else {
                // 用户未注销，则自动登录
                viewModel.autoLogin();
            }
        });

        // observe the result of login
        viewModel.getLoginResultLiveData().observe(this, loginResult -> {
            Log.d(TAG, "loginResult: called");
            if (loginResult == null) {
                return;
            }
            if (loginResult.getError() != null) {
                intentToLogin();
            }else {
                intentToMain();
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
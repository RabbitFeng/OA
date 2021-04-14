package com.example.demo02app;

import android.app.Application;

import com.example.demo02app.login.data.LoginRepository;

public class MyApplication extends Application {
    private MyExecutors myExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        myExecutors = new MyExecutors();
    }

    public MyExecutors getMyExecutors() {
        return myExecutors;
    }

    public LoginRepository getLoginRepository() {
        return LoginRepository.getInstance(getApplicationContext(),getMyExecutors());
    }
}


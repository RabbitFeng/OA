package com.example.demo02app;

import android.app.Application;

import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.repository.UserRepository;

public class MyApplication extends Application {
    public LoggedInUser currentUser;
    private MyExecutors myExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        myExecutors = new MyExecutors();
    }

    public MyExecutors getMyExecutors() {
        return myExecutors;
    }

    public UserRepository getLoginRepository() {
        return UserRepository.getInstance(getApplicationContext(), getMyExecutors());
    }

    public void setCurrentUser(LoggedInUser loggedInUser) {
        currentUser = loggedInUser;
    }

    public LoggedInUser getCurrentUser() {
        return currentUser;
    }
}


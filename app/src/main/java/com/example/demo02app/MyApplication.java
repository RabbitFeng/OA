package com.example.demo02app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.repository.AddressBookRepository;
import com.example.demo02app.repository.MessageRepository;
import com.example.demo02app.repository.UserRepository;

import java.util.Objects;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();
    private MyExecutors myExecutors;
    private AppDatabase appDatabase;

    private LiveData<LoggedInUser> loggedInUserLiveData;


    @Override
    public void onCreate() {
        super.onCreate();
        myExecutors = new MyExecutors();
        loggedInUserLiveData = UserRepository.getInstance(getApplicationContext(), myExecutors)
                .getUserCacheLiveData();
    }

    public MyExecutors getMyExecutors() {
        return myExecutors;
    }

    public AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getApplicationContext(), getMyExecutors());
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance(getApplicationContext(), getMyExecutors());
    }

    public AddressBookRepository getAddressBookRepository() {
        return AddressBookRepository.getInstance(getApplicationContext(), getAppDatabase(), getMyExecutors());
    }

    public MessageRepository getMessageRepository() {
        return MessageRepository.getInstance(this, getAppDatabase(), getMyExecutors());
    }

    public void setMyExecutors(MyExecutors myExecutors) {
        this.myExecutors = myExecutors;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public String getUserId() {
        return Objects.requireNonNull(getLoggedInUserLiveData().getValue()).getUserId();
    }

}


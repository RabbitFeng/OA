package com.example.demo02app;

import android.app.Application;

import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.repository.AddressBookRepository;
import com.example.demo02app.repository.UserRepository;

public class MyApplication extends Application {
    private MyExecutors myExecutors;
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        myExecutors = new MyExecutors();
    }

    public MyExecutors getMyExecutors() {
        return myExecutors;
    }

    public AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getApplicationContext(), getMyExecutors());
    }

    public UserRepository getLoginRepository() {
        return UserRepository.getInstance(getApplicationContext(), getMyExecutors());
    }

    public AddressBookRepository getAddressBookRepository() {
        return AddressBookRepository.getInstance(getApplicationContext(), getAppDatabase(), getMyExecutors());
    }

    public void setMyExecutors(MyExecutors myExecutors) {
        this.myExecutors = myExecutors;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}


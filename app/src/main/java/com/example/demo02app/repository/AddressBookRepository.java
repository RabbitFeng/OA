package com.example.demo02app.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.model.adressbook.data.AddressBookItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddressBookRepository {
    private volatile static AddressBookRepository sInstance;
    @NonNull
    private final Context appContext;
    @NonNull
    private final AppDatabase database;
    @NonNull
    private final MyExecutors executors;

    private AddressBookRepository(@NotNull Context appContext, @NonNull AppDatabase appDatabase, @NotNull MyExecutors executors) {
        this.appContext = appContext;
        this.database = appDatabase;
        this.executors = executors;
    }

    public static AddressBookRepository getInstance(Context appContext, AppDatabase appDatabase, MyExecutors executors) {
        if (sInstance == null) {
            synchronized (AddressBookRepository.class) {
                if (sInstance == null) {
                    sInstance = new AddressBookRepository(appContext.getApplicationContext(), appDatabase, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<AddressBookItem>> loadAddressBook(String uId) {

        return database.addressBookDao().selectAddressBook(uId);
    }
}

package com.example.demo02app.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.R;
import com.example.demo02app.db.AppDatabase;
import com.example.demo02app.db.entity.AddressBook;
import com.example.demo02app.model.addressbook.data.AddressBookItem;
import com.example.demo02app.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddressBookRepository {
    private static final String TAG = AddressBookRepository.class.getName();
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
        loadFromNet(uId);
        return database.addressBookDao().selectAddressBook(uId);
    }

    public void loadFromNet(String uId) {
        OkHttpUtil.post(getString(R.string.url_address_book), new HashMap<String, String>() {{
            put(getString(R.string.param_user_id), uId);
        }}).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: called");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: success");
                    String string = Objects.requireNonNull(response.body()).string();
                    // 存储到本地数据库
                    try {
                        List<AddressBook> addressBooks = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(string);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String userOther = jsonObject.getString(getString(R.string.param_ab_user_other));
                            String remark = jsonObject.getString(getString(R.string.param_ab_remark));
                            addressBooks.add(new AddressBook(uId,userOther,remark));
                        }
                        database.addressBookDao().insertAddressBook(addressBooks);
                        Log.d(TAG, "onResponse: insertSuccess");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onResponse: insertFail");
                    }

                    Log.d(TAG, "onResponse: string" + string);
                } else {
                    Log.d(TAG, "onResponse: failure");
                }
            }
        });

    }

    private String getString(@StringRes int stringRes) {
        return appContext.getString(stringRes);
    }
}

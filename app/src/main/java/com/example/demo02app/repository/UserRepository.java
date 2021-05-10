package com.example.demo02app.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.R;
import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.model.login.data.model.RegisterUser;
import com.example.demo02app.util.OkHttpUtil;
import com.example.demo02app.util.IdentityUtil;
import com.example.demo02app.util.exception.MyException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserRepository {

    private static final String TAG = UserRepository.class.getName();

    private volatile static UserRepository sInstance;

    /**
     * 是否注销
     */
    private final MediatorLiveData<Boolean> isLogoutLiveData = new MediatorLiveData<>();

    /**
     * 本地缓存的用户信息
     */
    private final MutableLiveData<LoggedInUser> userCacheLiveData = new MutableLiveData<>();

    /**
     * 是否在加载缓存数据
     */
    private final MediatorLiveData<Boolean> isLoadingLiveData = new MediatorLiveData<>();

    @NonNull
    private final Context appContext;

    @NonNull
    private final MyExecutors executors;

    private UserRepository(@NonNull Context appContext, @NonNull MyExecutors executors) {
        this.appContext = appContext;
        this.executors = executors;

        isLoadingLiveData.setValue(true);
        // 监听UserCache变化，是否在加载缓存用户数据
        isLoadingLiveData.addSource(userCacheLiveData, userCache -> {
            isLoadingLiveData.postValue(userCache == null);
            if (userCache != null) {
                isLogoutLiveData.postValue(userCache.isLogout());
            }
        });
        // 监听UserCache变化，用户是否注销
        isLogoutLiveData.addSource(userCacheLiveData, userCache -> {
            if (userCache != null) {
                isLogoutLiveData.postValue(userCache.isLogout());
            }
        });
        // 读取本地缓存
        readLocalCache();
    }

    /**
     * 获取单例
     *
     * @param context   appContext
     * @param executors 线程池
     * @return LoginRepository单例
     */
    public static UserRepository getInstance(@NonNull Context context, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (UserRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(context.getApplicationContext(), executors);
                }
            }
        }
        return sInstance;
    }

    public void login(@NonNull final LoggedInUser loggedInUser, @NonNull RepositoryCallback<LoggedInUser> callback) {
        OkHttpUtil.post(getString(R.string.url_login), convertLoggedUserToMap(loggedInUser)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onComplete(new Result.Error<>(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    try {
//                        LoggedInUser user = generateLoggedInUserFromJson(string);
                        // 解析返回的字符串
                        generateLoggedInUserFromJson(loggedInUser, string);
                        callback.onComplete(new Result.Success<>(loggedInUser));
                        updateUserCache(loggedInUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onComplete(new Result.Error<>(e));
                    }
                }
            }
        });
    }

    /**
     * 退出登录
     */
    public void logout() {
        Objects.requireNonNull(userCacheLiveData.getValue()).setLogout(true);
        executors.diskIO().execute(() -> {
            SharedPreferences sharedPreferences =
                    appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.pref_logout), true).apply();
        });
    }

    /**
     * @param registerUser
     * @param callback
     */
    public void register(@NonNull final RegisterUser registerUser, RepositoryCallback<LoggedInUser> callback) {

        OkHttpUtil.post(getString(R.string.url_register), convertRegisterUserToMap(registerUser)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onComplete(new Result.Error<>(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    try {
                        LoggedInUser loggedInUser = new LoggedInUser();
                        loggedInUser.setUsername(registerUser.getPhone());
                        loggedInUser.setPassword(registerUser.getPassword());
                        generateLoggedInUserFromJson(loggedInUser, string);
                        callback.onComplete(new Result.Success<>(loggedInUser));
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onComplete(new Result.Error<>(e));
                    }
                }
            }
        });
    }

    // Getters
    public LiveData<Boolean> getIsLogoutLiveData() {
        return isLogoutLiveData;
    }

    public LiveData<LoggedInUser> getUserCacheLiveData() {
        return userCacheLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    // update
    public void updateUserCache(@NonNull LoggedInUser user) {
        Log.d(TAG, "updateUserCache: called");
        userCacheLiveData.postValue(user);
        updateLocalCache(user);
    }

    // 更新本地
    public void updateLocalCache(@NonNull LoggedInUser user) {
        executors.diskIO().execute(() -> {
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .putString(getString(R.string.pref_user_id), user.getUserId())
                    .putString(getString(R.string.pref_username), user.getUsername())
                    .putString(getString(R.string.pref_password), user.getPassword())
                    .putInt(getString(R.string.pref_identity), user.getIdentity())
                    .putBoolean(getString(R.string.pref_logout), user.isLogout())
                    .apply();
        });
    }

    // 读取本地缓存
    public void readLocalCache() {
        executors.diskIO().execute(() -> {
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString(getString(R.string.pref_user_id), "");
            String username = sharedPreferences.getString(getString(R.string.pref_username), "");
            String password = sharedPreferences.getString(getString(R.string.pref_password), "");
            int permission = sharedPreferences.getInt(getString(R.string.pref_identity), IdentityUtil.IDENTITY_EMPLOYEE);
            boolean isLogout = sharedPreferences.getBoolean(getString(R.string.pref_logout), true);
            LoggedInUser userCacheLocal = new LoggedInUser(userId, username, password, permission, isLogout);
            userCacheLiveData.postValue(userCacheLocal);
        });
    }

    // Converters
    private Map<String, String> convertLoggedUserToMap(@NonNull LoggedInUser loggedInUser) {
        return new HashMap<String, String>() {
            {
                put(getString(R.string.param_username), loggedInUser.getUsername());
                put(getString(R.string.param_password), loggedInUser.getPassword());
            }
        };
    }

    private Map<String, String> convertRegisterUserToMap(@NonNull RegisterUser registerUser) {
        return new HashMap<String, String>() {{
            put(getString(R.string.param_username), registerUser.getPhone());
            put(getString(R.string.param_password), registerUser.getPassword());
            put(getString(R.string.param_real_name), registerUser.getRealName());
            put(getString(R.string.param_identity), String.valueOf(registerUser.getIdentity()));
        }};
    }

    private void generateLoggedInUserFromJson(@NonNull LoggedInUser loggedInUser, @NonNull String json) throws MyException, JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int result = jsonObject.getInt("result");
        if (result != Result.RESULT_SUCCESS) {
            throw new MyException();
        }
        JSONObject data = jsonObject.getJSONObject("data");
        loggedInUser.setUserId(data.getString(getString(R.string.param_user_id)));
        loggedInUser.setIdentity(data.getInt(getString(R.string.param_identity)));
        loggedInUser.setLogout(false);
    }

//    private void generateRegisterUserFromJson(@NonNull RegisterUser registerUser, @NonNull String json) throws MyException, JSONException {
//        JSONObject jsonObject = new JSONObject(json);
//        int result = jsonObject.getInt("result");
//        if (result != Result.RESULT_SUCCESS) {
//            throw new MyException();
//        }
//        JSONObject data = jsonObject.getJSONObject("data");
//
//        data.getString(getString(R.string.param_username));
//    }

    private String getString(@StringRes Integer resId) {
        return appContext.getString(resId);
    }
}

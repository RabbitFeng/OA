package com.example.demo02app.login.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.R;
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.model.LoggedInUser;
import com.example.demo02app.login.data.model.RegisterUser;
import com.example.demo02app.util.OkHttpUtil;
import com.example.demo02app.util.PermissionsUtil;

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

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getName();

    private volatile static LoginRepository sInstance;

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

    private LoginRepository(@NonNull Context appContext, @NonNull MyExecutors executors) {
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
        init();
    }

    private void init() {
        executors.diskIO().execute(() -> {
            // 读取本地缓存用户信息
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(getString(R.string.pref_username), "");
            String password = sharedPreferences.getString(getString(R.string.pref_password), "");
            int permission = sharedPreferences.getInt(getString(R.string.pref_permissions), PermissionsUtil.PERMISSION_EMPLOYEE);
            boolean isLogout = sharedPreferences.getBoolean(getString(R.string.pref_logout), true);
            LoggedInUser userCacheLocal = new LoggedInUser(username, password, permission, isLogout);
            userCacheLiveData.postValue(userCacheLocal);
        });
    }

    /**
     * 获取单例
     *
     * @param context   appContext
     * @param executors 线程池
     * @return LoginRepository单例
     */
    public static LoginRepository getInstance(@NonNull Context context, @NonNull MyExecutors executors) {
        if (sInstance == null) {
            synchronized (LoginRepository.class) {
                if (sInstance == null) {
                    sInstance = new LoginRepository(context.getApplicationContext(), executors);
                }
            }
        }
        return sInstance;
    }

    /**
     * 默认登录
     * @param callback 回调接口
     * @throws IOException IOException
     */
    public void login(@NonNull MyCallback<LoginResult> callback) throws IOException {
        LoggedInUser user = userCacheLiveData.getValue();
        if (user == null) {
            throw new IOException("You must observe isLoadingLiveData to ensure " +
                    "that userCacheLiveData get the cache from local data source");
        } else {
            login(user, callback);
        }
    }

    /**
     * 登录
     *
     * @param loggedInUser loggedInUser
     * @param callback 回调接口
     */
    public void login(@NonNull LoggedInUser loggedInUser, @NonNull MyCallback<LoginResult> callback) {
        OkHttpUtil.postAsync(getString(R.string.url_login), convertLoggedUserToMap(loggedInUser), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt("result");
                        callback.onSuccess(new LoginResult(result));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }

            }
        });
    }

    /**
     * 注册
     * @param registerUser registerUser
     * @param callback 回调接口
     */
    public void register(RegisterUser registerUser, MyCallback<RegisterResult> callback) {
        OkHttpUtil.postAsync(getString(R.string.url_register), convertRegisterUserToMap(registerUser), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // register Fail
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        int result = jsonObject.getInt("result");
                        callback.onSuccess(new RegisterResult(result));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }
            }
        });
    }

    // Getters
    public LiveData<Boolean> getIsLogoutLiveData() {
        return isLogoutLiveData;
    }

    public MutableLiveData<LoggedInUser> getUserCacheLiveData() {
        return userCacheLiveData;
    }

    public MediatorLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
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
            put(getString(R.string.param_phone), registerUser.getPhone());
            put(getString(R.string.param_password), registerUser.getPassword());
        }};
    }

    private String getString(@StringRes Integer resId) {
        return appContext.getString(resId);
    }
}

package com.example.demo02app.login.data;

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
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.model.LoggedInUser;
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
    /**
     * 单例
     */
    private volatile static LoginRepository sInstance;

    /**
     * 是否注销
     */
    private final MediatorLiveData<Boolean> isLogoutLiveData = new MediatorLiveData<>();

    /**
     * 当前操作用户
     */
    private final MutableLiveData<LoggedInUser> loggedInUserLiveData = new MutableLiveData<>();

    /**
     * 登录状态
     */
    private final MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    /**
     * 本地缓存的用户信息
     */
    private final MutableLiveData<LoggedInUser> userCacheLiveData = new MutableLiveData<>();

    /**
     * 是否在加载缓存数据
     */
    private final MediatorLiveData<Boolean> isLoading = new MediatorLiveData<>();

    @NonNull
    Context appContext;

    @NonNull
    private LoginDataSource loginDataSource;

    @NonNull
    private MyExecutors executors;

    private LoginRepository(@NonNull Context appContext, @NonNull MyExecutors executors) {
        this.appContext = appContext;
        this.executors = executors;
        this.loginDataSource = new LoginDataSource(appContext);

        isLoading.setValue(true);
        isLoading.addSource(userCacheLiveData, userCache -> {
            isLoading.postValue(userCache == null);
            if (userCache != null) {
                isLogoutLiveData.postValue(userCache.isLogout());
            }
        });
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
     * 搭配isLoading
     */
    public void login() {
        LoggedInUser user = loggedInUserLiveData.getValue() == null ?
                userCacheLiveData.getValue() : loggedInUserLiveData.getValue();
        login(user);
    }

    /**
     * 登录
     *
     * @param user user
     */
    public void login(LoggedInUser user) {
        if (user == null) {
            Log.d(TAG, "login: loggedInUser is null");
            loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
        } else {
            Log.d(TAG, "login: " + "[username:" + user.getUsername() + "," +
                    "password:" + user.getPassword() + "]");
            OkHttpUtil.postAsync(getString(R.string.url_login), convertLoggedUserToMap(user), new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
                        return;
                    }
                    String s = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        Log.d(TAG, "onResponse: json:" + jsonObject.toString());
                        int result = jsonObject.getInt("result");
                        switch (result) {
                            default:
                                loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
                            case LoginResult.USERNAME_NOT_EXIT:
                                loginResultLiveData.postValue(new LoginResult(LoginResult.USERNAME_NOT_EXIT));
                                break;
                            case LoginResult.PASSWORD_ERROR:
                                loginResultLiveData.postValue(new LoginResult(LoginResult.PASSWORD_ERROR));
                                break;
                            case LoginResult.SUCCESS:
                                loginResultLiveData.postValue(new LoginResult(LoginResult.SUCCESS));
                                loginDataSource.addUserCacheLocal(user);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
                    }
                }
            });
        }
    }

//    /**
//     * 登录
//     *
//     * @param loggedInUser
//     * @param myCallback
//     */
//    public void login(LoggedInUser loggedInUser) {
//        OkHttpUtil.postAsync(getString(R.string.url_login), convertLoggedUserToMap(loggedInUser), new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                // 登录失败
//                myCallback.onFailure();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String s = Objects.requireNonNull(response.body()).string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    int result = jsonObject.getInt("result");
//                    switch (result) {
//                        default:
//                        case LoginResult.USERNAME_NOT_EXIT:
//                            loginResultLiveData.postValue(new LoginResult(LoginResult.USERNAME_NOT_EXIT));
//                            break;
//                        case LoginResult.PASSWORD_ERROR:
//                            loginResultLiveData.postValue(new LoginResult(LoginResult.PASSWORD_ERROR));
//                            break;
//                        case LoginResult.SUCCESS:
//                            myCallback.onSuccess(loggedInUser);
//                            break;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    myCallback.onFailure();
//                }
//            }
//        });
//    }

    /**
     * 注册
     *
     * @param user
     * @param callback
     */
    public void register(LoggedInUser user, MyCallback<LoggedInUser> callback) {
        Map<String, String> map = new HashMap<String, String>() {{
            put(getString(R.string.param_username), user.getUsername());
            put(getString(R.string.param_password), user.getPassword());
        }};
        OkHttpUtil.postAsync(getString(R.string.url_register), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        callback.onFailure();
                    }
                }).start();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 获取服务端返回值
                // String str = response.body().string();
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LoggedInUser fakeUser = user;
                    callback.onSuccess(fakeUser);
                }).start();
            }
        });
    }

    // Getters
    public LiveData<Boolean> getIsLogoutLiveData() {
        return isLogoutLiveData;
    }

    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public MutableLiveData<LoggedInUser> getUserCacheLiveData() {
        return userCacheLiveData;
    }

    public MutableLiveData<LoginResult> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public MediatorLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private Map<String, String> convertLoggedUserToMap(LoggedInUser loggedInUser) {
        return new HashMap<String, String>() {
            {
                put(getString(R.string.param_username), loggedInUser.getUsername());
                put(getString(R.string.param_password), loggedInUser.getPassword());
            }
        };
    }

    private String getString(@StringRes Integer resId) {
        return appContext.getString(resId);
    }
}

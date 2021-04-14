package com.example.demo02app.login.ui;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.login.data.LoginFormState;
import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.LoginResult;
import com.example.demo02app.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getName();
    private LoginRepository loginRepository;

    // 登录用户信息
    private final MutableLiveData<LoggedInUser> loggedInUserLiveData;

    // 登录表单状态
    private final MutableLiveData<LoginFormState> loginFormStateLiveData = new MutableLiveData<>();

    // 登录结果
    private final MutableLiveData<LoginResult> loginResultLiveData;

    public LoginViewModel(@NonNull LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        loggedInUserLiveData = loginRepository.getUserCacheLiveData();
        loginResultLiveData = loginRepository.getLoginResultLiveData();
    }

    public void update() {
        LoggedInUser value = loggedInUserLiveData.getValue();
        if (value == null) {
            loginFormStateLiveData.postValue(new LoginFormState(R.string.invalid_username, R.string.invalid_password));
        } else if (!isUsernameValid(value.getUsername())) {
            loginFormStateLiveData.postValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(value.getPassword())) {
            loginFormStateLiveData.postValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormStateLiveData.postValue(new LoginFormState(true));
        }
    }

    public void login() {
        Log.d(TAG, "login: called");

        loginRepository.login();
//        loginRepository.login(new MyCallback<LoggedInUser>() {
//            @Override
//            public void onSuccess(LoggedInUser loggedInUser) {
//                // 登录成功
//                Log.d(TAG, "onSuccess: 登录成功");
//            }
//
//            @Override
//            public void onFailure() {
//                Log.d(TAG, "onFailure: 登录失败");
//            }
//        });
//        if (user == null) {
//            Log.d(TAG, "login: fail, cause user is null");
//            loginFormStateLiveData.postValue(new LoginFormState(R.string.invalid_username, R.string.invalid_password));
//        } else if (!isUsernameValid(user.getUsername())) {
//            loginFormStateLiveData.postValue(new LoginFormState(R.string.invalid_username, null));
//        } else if (!isPasswordValid(user.getPassword())) {
//            loginFormStateLiveData.postValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormStateLiveData.postValue(new LoginFormState(true));
//            loginRepository.login(user, new MyCallback<LoggedInUser>() {
//                @Override
//                public void onSuccess(LoggedInUser loggedInUser) {
//                    Log.d(TAG, "onSuccess: called");
//                    // 登录成功
//                    isLogin.postValue(true);
//                }
//
//                @Override
//                public void onFailure() {
//                    Log.d(TAG, "onFailure: called");
//                }
//            });
//        }
    }

    /***
     * 校验username格式
     * @param username
     * @return
     */
    private boolean isUsernameValid(String username) {
        if (username == null) {
            return false;
        }

        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
        if (username.contains(" ")) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    /**
     * 校验password格式
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return !password.trim().isEmpty();
    }

    // Getters
    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        Log.d(TAG, "getLoggedInUserLiveData: called");
        return loggedInUserLiveData;
    }

    public LiveData<LoginFormState> getLoginFormStateLiveData() {
        return loginFormStateLiveData;
    }

    public LiveData<LoginResult> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        @NonNull
        private final LoginRepository loginRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.loginRepository = ((MyApplication) application).getLoginRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(LoginViewModel.class)) {
                return (T) new LoginViewModel(loginRepository);
            } else {
                throw new IllegalArgumentException("Not LoginViewModel class");
            }
//            return super.create(modelClass);

        }
    }
}

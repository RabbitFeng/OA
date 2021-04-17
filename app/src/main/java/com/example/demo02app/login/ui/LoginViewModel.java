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
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.LoginFormState;
import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.LoginResult;
import com.example.demo02app.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getName();
    private final LoginRepository loginRepository;

    // 登录用户信息
    private final MutableLiveData<LoggedInUser> loggedInUserLiveData = new MutableLiveData<>();

    // 登录表单状态
    private final MutableLiveData<LoginFormState> loginFormStateLiveData = new MutableLiveData<>();

    // 登录结果
    private final MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        // 使用双向绑定，必须赋初值
        loggedInUserLiveData.setValue(new LoggedInUser("", ""));
        loggedInUserLiveData.postValue(loginRepository.getUserCacheLiveData().getValue());
    }

    /**
     * 更新表单状态
     */
    public void updateFormState() {
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
        LoggedInUser value = loggedInUserLiveData.getValue();
        if (value == null) {
            loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
            Log.d(TAG, "login: value is null");
            return;
        }
        // 进行login
        loginRepository.login(loggedInUserLiveData.getValue(), new MyCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResultLiveData.postValue(loginResult);
            }

            @Override
            public void onFailure() {
                loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
            }
        });
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
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
//            return super.create(modelClass);

        }
    }
}

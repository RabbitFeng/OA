package com.example.demo02app.model.login.ui;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.model.login.data.LoginFormState;
import com.example.demo02app.model.login.data.LoginResult;
import com.example.demo02app.model.login.data.entity.LoggedInUser;
import com.example.demo02app.repository.UserRepository;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getName();

    @NonNull
    private final UserRepository userRepository;

    private final MediatorLiveData<LoggedInUser> loggedInUserLiveData = new MediatorLiveData<>();

    private final MutableLiveData<LoginFormState> loginFormStateLiveData = new MutableLiveData<>();

    private final MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
        // You must ensure that loggedInUserLiveData are initial values for data binding
        loggedInUserLiveData.setValue(new LoggedInUser("", ""));
        // Observe userCacheLiveData to load the local cache
        loggedInUserLiveData.addSource(userRepository.getUserCacheLiveData(), loggedInUserLiveData::postValue);
    }

    /**
     * Update loginFormStateLiveData
     */
    public void updateFormState() {
        Log.d(TAG, "updateFormState: called");
        LoggedInUser value = loggedInUserLiveData.getValue();
        if (value == null) {
            loginFormStateLiveData.postValue(new LoginFormState(R.string.ui_username_invalid, R.string.ui_password_invalid));
        } else if (!isUsernameValid(value.getUsername())) {
            loginFormStateLiveData.postValue(new LoginFormState(R.string.ui_username_invalid, null));
        } else if (!isPasswordValid(value.getPassword())) {
            loginFormStateLiveData.postValue(new LoginFormState(null, R.string.ui_password_invalid));
        } else {
            loginFormStateLiveData.postValue(new LoginFormState(true));
        }
    }

    /***
     * Login
     */
    public void login() {
        Log.d(TAG, "login: called");
        LoggedInUser value = loggedInUserLiveData.getValue();
        if (value == null) {
            Log.d(TAG, "login: value is null");
            loginResultLiveData.postValue(new LoginResult(R.string.ui_login_failed));
            return;
        }
        // login with LoggedInUser
        userRepository.login(value, new RepositoryCallback<LoggedInUser>() {
            @Override
            public void onComplete(Result<LoggedInUser> t) {
                if (t instanceof Result.Success) {
                    // login succeed
                    LoggedInUser data = ((Result.Success<LoggedInUser>) t).getData();
                    loginResultLiveData.postValue(new LoginResult(data));
                } else {
                    loginResultLiveData.postValue(new LoginResult(R.string.ui_login_failed));
                }
            }
        });
    }

    /***
     * Verify username
     * @param username username
     * @return 'true' if username is valid, 'false' otherwise.
     */
    private boolean isUsernameValid(String username) {
        if (username == null) {
            return false;
        }

        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
        return !username.trim().isEmpty();
    }

    /**
     * Verify password
     *
     * @param password password
     * @return 'true' if password is valid, 'false' otherwise
     */
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return password.trim().length() >= 6;
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
        private final UserRepository userRepository;

        public Factory(@NonNull Application application) {
            this.userRepository = ((MyApplication) application).getUserRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(LoginViewModel.class)) {
                return (T) new LoginViewModel(userRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

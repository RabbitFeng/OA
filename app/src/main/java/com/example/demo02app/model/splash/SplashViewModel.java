package com.example.demo02app.model.splash;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.model.login.data.LoginResult;
import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.repository.UserRepository;

public class SplashViewModel extends ViewModel {
    private static final String TAG = SplashViewModel.class.getName();
    @NonNull
    private final UserRepository userRepository;

    /**
     * 用户是否注销
     */
    private final MediatorLiveData<LoggedInUser> loggedInUserLiveData = new MediatorLiveData<>();

    /**
     * 登录结果
     */
    private final MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    private SplashViewModel(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
        loggedInUserLiveData.addSource(userRepository.getUserCacheLiveData(), new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {
                // cache更新，死循环
                Log.d(TAG, "onChanged: called " + loggedInUser);
                if (loggedInUser != null) {
                    loggedInUserLiveData.postValue(loggedInUser);
                }
            }
        });
    }

    public void autoLogin() {
        // 取消对userCache的观察
        loggedInUserLiveData.removeSource(userRepository.getUserCacheLiveData());

        Log.d(TAG, "autoLogin: called");
        if (loggedInUserLiveData.getValue() == null) {
            loginResultLiveData.postValue(new LoginResult(R.string.ui_login_failed));
        } else {
            loginResultLiveData.postValue(new LoginResult(loggedInUserLiveData.getValue()));

//            userRepository.login(loggedInUserLiveData.getValue(), new RepositoryCallback<LoggedInUser>() {
//                @Override
//                public void onComplete(Result<LoggedInUser> t) {
//                    if (t instanceof Result.Success) {
//                        LoggedInUser data = ((Result.Success<LoggedInUser>) t).getData();
//                        loginResultLiveData.postValue(new LoginResult(data));
//                    } else {
//                        loginResultLiveData.postValue(new LoginResult(R.string.ui_login_failed));
//                    }
//                }
//            });
        }
    }

    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public LiveData<LoginResult> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final UserRepository userRepository;

        public Factory(@NonNull Application application) {
            userRepository = ((MyApplication) application).getUserRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(SplashViewModel.class)) {
                return (T) new SplashViewModel(userRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

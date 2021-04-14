package com.example.demo02app.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.LoginResult;

public class SplashViewModel extends ViewModel {
    private static final String TAG = SplashViewModel.class.getName();
    @NonNull
    private LoginRepository loginRepository;

    /**
     * 用户是否注销
     */
    private LiveData<Boolean> isLogoutLiveData;

    /**
     * 登录结果
     */
    private MutableLiveData<LoginResult> LoginResultLiveData;

    private SplashViewModel(@NonNull LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        LoginResultLiveData = loginRepository.getLoginResultLiveData();
        isLogoutLiveData = loginRepository.getIsLogoutLiveData();
    }

    public void autoLogin() {
        loginRepository.login();
    }

    // Getters
    public LiveData<Boolean> getIsLogoutLiveData() {
        return isLogoutLiveData;
    }

    public LiveData<LoginResult> getLoginResultLiveData() {
        return LoginResultLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private Application application;
        @NonNull
        private LoginRepository loginRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            loginRepository = ((MyApplication) application).getLoginRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return super.create(modelClass);
            if (modelClass.isAssignableFrom(SplashViewModel.class)) {
                return (T) new SplashViewModel(loginRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

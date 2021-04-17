package com.example.demo02app.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.LoginResult;

import java.io.IOException;

public class SplashViewModel extends ViewModel {
    private static final String TAG = SplashViewModel.class.getName();
    @NonNull
    private final LoginRepository loginRepository;

    /**
     * 用户是否注销
     */
    private final LiveData<Boolean> isLogoutLiveData;

    /**
     * 登录结果
     */
    private final MutableLiveData<LoginResult> loginResultLiveData = new MutableLiveData<>();

    private SplashViewModel(@NonNull LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        isLogoutLiveData = loginRepository.getIsLogoutLiveData();
    }

    public void autoLogin() {
        try {
            loginRepository.login(new MyCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    loginResultLiveData.postValue(loginResult);
                }

                @Override
                public void onFailure() {
                    loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            loginResultLiveData.postValue(new LoginResult(LoginResult.FAILURE));
        }
    }

    // Getters
    public LiveData<Boolean> getIsLogoutLiveData() {
        return isLogoutLiveData;
    }

    public LiveData<LoginResult> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final LoginRepository loginRepository;

        public Factory(@NonNull Application application) {
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

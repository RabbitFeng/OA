package com.example.demo02app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.repository.UserRepository;

public class MainViewModel extends ViewModel {

    private LiveData<LoggedInUser> loggedInUserLiveData;

    @NonNull
    private final UserRepository userRepository;

    public MainViewModel(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
        loggedInUserLiveData = userRepository.getUserCacheLiveData();
    }

    public void logout() {
        // 退出登录
        userRepository.logout();
    }

    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final UserRepository userRepository;

        public Factory(@NonNull Application application) {
            this.userRepository = ((MyApplication) application).getLoginRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainViewModel.class)) {
                return (T) new MainViewModel(userRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

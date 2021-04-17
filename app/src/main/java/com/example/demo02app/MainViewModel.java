package com.example.demo02app;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.model.LoggedInUser;

public class MainViewModel extends ViewModel {

    private MutableLiveData<LoggedInUser> loggedInUserLiveData;

    @NonNull
    private final LoginRepository loginRepository;

    public MainViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public MutableLiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final LoginRepository loginRepository;

        public Factory(@NonNull LoginRepository loginRepository) {
            this.loginRepository = loginRepository;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainViewModel.class)) {
                return (T) new MainViewModel(loginRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

package com.example.demo02app.model.mine.info;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.login.data.entity.LoggedInUser;
import com.example.demo02app.repository.RepositoryCallback;
import com.example.demo02app.repository.Result;
import com.example.demo02app.repository.UserRepository;

public class UserInfoViewModel extends AndroidViewModel {

    private static final String TAG = UserInfoViewModel.class.getName();
    private final UserRepository userRepository;
    private final LiveData<LoggedInUser> loggedInUserLiveData;
    private final MutableLiveData<Boolean> logoutLiveDate;

    public UserInfoViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
        loggedInUserLiveData = userRepository.getUserCacheLiveData();
        logoutLiveDate = new MutableLiveData<>(false);
    }

    public void logout() {
        userRepository.logout(new RepositoryCallback<Boolean>() {
            @Override
            public void onComplete(Result<Boolean> t) {
                Log.d(TAG, "onComplete: called");
                if (t instanceof Result.Success) {
                    //
                    logoutLiveDate.postValue(true);
                }
            }
        });
    }

    public LiveData<LoggedInUser> getLoggedInUserLiveData() {
        return loggedInUserLiveData;
    }

    public LiveData<Boolean> getLogoutLiveDate() {
        return logoutLiveDate;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final UserRepository userRepository;

        public Factory(Application application) {
            this.application = application;
            this.userRepository = ((MyApplication) application).getUserRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserInfoViewModel(application, userRepository);
        }
    }
}
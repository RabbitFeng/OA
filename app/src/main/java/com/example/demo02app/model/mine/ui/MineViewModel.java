package com.example.demo02app.model.mine.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.repository.UserRepository;

public class MineViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public MineViewModel(@NonNull Application application,UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    // 更新头像
    public void updateProfilePic(){

    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory{
        private final Application application;

        private final UserRepository userRepository;

        public Factory(Application application) {
            this.application = application;
            this.userRepository = ((MyApplication)application).getUserRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return super.create(modelClass);
            return (T) new MineViewModel(application,userRepository);
        }
    }

}
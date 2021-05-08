package com.example.demo02app.model.chat.ui;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModel extends AndroidViewModel {

    private String userOther;

    public ChatViewModel(@NonNull Application application, String userOther) {
        super(application);
        this.userOther = userOther;
    }


    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final String userOther;
        private final Application application;

        public Factory(Application application, String userOther) {
            this.application = application;
            this.userOther = userOther;
        }

        @NonNull
        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ChatViewModel.class)) {
                return (T) new ChatViewModel(application, userOther);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
package com.example.demo02app.model.chat.ui;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModel extends ViewModel {


    public static final class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(ChatViewModel.class)){
                return (T) new ChatViewModel();
            }else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
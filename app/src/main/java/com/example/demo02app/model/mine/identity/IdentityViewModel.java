package com.example.demo02app.model.mine.identity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.model.mine.data.IdentityRegister;

public class IdentityViewModel extends ViewModel {
    private final IdentityRegister identityRegister = new IdentityRegister();

    public IdentityRegister getIdentityRegister() {
        return identityRegister;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(IdentityViewModel.class)) {
                return (T) new IdentityViewModel();
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
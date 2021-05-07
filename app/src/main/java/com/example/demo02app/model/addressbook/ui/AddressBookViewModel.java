package com.example.demo02app.model.addressbook.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddressBookViewModel extends AndroidViewModel {

    private String userOther;

    private AddressBookViewModel(@NonNull Application application, @NonNull String userOther) {
        super(application);

        this.userOther = userOther;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final String userOther;

        public Factory(Application application, String userOther) {
            this.application = application;
            this.userOther = userOther;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AddressBookViewModel(application, userOther);
        }
    }
}
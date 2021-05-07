package com.example.demo02app.model.addressbook.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.model.addressbook.data.AddressBookItem;
import com.example.demo02app.repository.AddressBookRepository;

import java.util.List;

public class AddressBookViewModel extends ViewModel {
    private static final String TAG = AddressBookViewModel.class.getName();
    private final Application application;

    private final AddressBookRepository addressBookRepository;

    private final String currentUserId;

    private final LiveData<List<AddressBookItem>> addressBookItemLiveData;

    public AddressBookViewModel(Application application, AddressBookRepository addressBookRepository,
                                String currentUserId) {
        this.application = application;
        this.addressBookRepository = addressBookRepository;
        this.currentUserId = currentUserId;
        addressBookItemLiveData = addressBookRepository.loadAddressBook(currentUserId);
        Log.d(TAG, "AddressBookViewModel: userID " + currentUserId);
    }

    public void reLoad() {
        addressBookRepository.loadFromNet(currentUserId);
    }

    public LiveData<List<AddressBookItem>> getAddressBookListLiveData() {
        return addressBookItemLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final AddressBookRepository addressBookRepository;
        private final String currentUserId;

        public Factory(Application application, String currentUserId) {
            this.application = application;
            addressBookRepository = ((MyApplication) application).getAddressBookRepository();
            this.currentUserId = currentUserId;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AddressBookViewModel.class)) {
                return (T) new AddressBookViewModel(application, addressBookRepository, currentUserId);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
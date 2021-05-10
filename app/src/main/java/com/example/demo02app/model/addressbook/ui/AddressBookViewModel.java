package com.example.demo02app.model.addressbook.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.db.data.AddressBookDO;
import com.example.demo02app.repository.AddressBookRepository;

public class AddressBookViewModel extends AndroidViewModel {

    private String userOther;

    private LiveData<AddressBookDO> addressBookLiveData;

    private AddressBookRepository addressBookRepository;

    private AddressBookViewModel(@NonNull Application application, @NonNull String userOther,
                                 AddressBookRepository addressBookRepository) {
        super(application);
        this.userOther = userOther;
        this.addressBookRepository = addressBookRepository;
//        addressBookLiveData = addressBookRepository.findAddressBookByUserId(null, userOther);
    }

    public String getUserOther(){
        return userOther;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final String userOther;
        private final AddressBookRepository addressBookRepository;

        public Factory(Application application, String userOther) {
            this.application = application;
            this.userOther = userOther;
            this.addressBookRepository = ((MyApplication) application).getAddressBookRepository();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AddressBookViewModel(application, userOther, addressBookRepository);
        }
    }
}
package com.example.demo02app.login.ui;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.LoginRepository;
import com.example.demo02app.login.data.RegisterFormState;
import com.example.demo02app.login.data.RegisterResult;
import com.example.demo02app.login.data.model.RegisterUser;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = RegisterViewModel.class.getName();

    private final LoginRepository loginRepository;

    private final MutableLiveData<RegisterUser> registerUserLiveData = new MutableLiveData<>();

    private final MutableLiveData<RegisterFormState> registerFormStateLiveData = new MutableLiveData<>();

    private final MutableLiveData<RegisterResult> registerResultLiveData = new MutableLiveData<>();

    public RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        registerUserLiveData.setValue(new RegisterUser("", ""));
    }

    /**
     * 注册
     */
    public void register() {
        loginRepository.register(registerUserLiveData.getValue(), new MyCallback<RegisterResult>() {
            @Override
            public void onSuccess(RegisterResult registerResult) {
                registerResultLiveData.postValue(registerResult);
            }

            @Override
            public void onFailure() {
                registerResultLiveData.postValue(new RegisterResult(RegisterResult.FAILURE));
            }
        });

    }

    public void updateFormState() {
        RegisterUser user = registerUserLiveData.getValue();
        if (user == null) {
            registerFormStateLiveData.setValue(new RegisterFormState(R.string.ui_phone_invalid, R.string.ui_password_invalid));
        } else if (!isPhoneInvalid(user.getPhone())) {
            registerFormStateLiveData.setValue(new RegisterFormState(R.string.ui_phone_invalid, null));
        } else if (!isPasswordInvalid(user.getPassword())) {
            registerFormStateLiveData.setValue(new RegisterFormState(null, R.string.ui_password_invalid));
        }
    }

    public boolean isPhoneInvalid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    private boolean isPasswordInvalid(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return true;
    }


    // Getters
    public LiveData<RegisterUser> getRegisterUserLiveData() {
        return registerUserLiveData;
    }

    public LiveData<RegisterFormState> getRegisterFormStateLiveData() {
        return registerFormStateLiveData;
    }

    public LiveData<RegisterResult> getRegisterResultLiveData() {
        return registerResultLiveData;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;

        private final LoginRepository loginRepository;

        public Factory(Application application) {
            this.application = application;
            loginRepository = ((MyApplication) application).getLoginRepository();
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
                return (T) new RegisterViewModel(loginRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

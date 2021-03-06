package com.example.demo02app.model.login.ui;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.model.login.data.RegisterFormState;
import com.example.demo02app.model.login.data.RegisterResult;
import com.example.demo02app.model.login.data.entity.LoggedInUser;
import com.example.demo02app.model.login.data.entity.RegisterUser;
import com.example.demo02app.repository.Result;
import com.example.demo02app.repository.UserRepository;
import com.example.demo02app.util.IdentityUtil;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = RegisterViewModel.class.getName();

    @NonNull
    private final UserRepository userRepository;

    private final MutableLiveData<RegisterUser> registerUserLiveData = new MutableLiveData<>();

    private final MutableLiveData<RegisterFormState> registerFormStateLiveData = new MutableLiveData<>();

    private final MutableLiveData<RegisterResult> registerResultLiveData = new MutableLiveData<>();

    public RegisterViewModel(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
        // Ensure that the registerUserLiveData has an initial value to user data binding
        registerUserLiveData.setValue(new RegisterUser("", "", "", IdentityUtil.IDENTITY_EMPLOYEE));
    }

    /**
     * register
     */
    public void register() {
        Log.d(TAG, "register: called");
        Log.d(TAG, "register: " + registerUserLiveData.getValue());
        RegisterUser user = registerUserLiveData.getValue();
        if (user == null) {
            Log.d(TAG, "register: user is null");
            registerResultLiveData.postValue(new RegisterResult(R.string.ui_register_failed));
            return;
        }
        userRepository.register(user, result -> {
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                registerResultLiveData.postValue(new RegisterResult(data));
                userRepository.updateUserCache(data);
            } else {
                registerResultLiveData.postValue(new RegisterResult(R.string.ui_register_failed));
            }
        });
    }

    /**
     * UpdateFormState
     */
    public void updateFormState() {
        Log.d(TAG, "updateFormState: called");
        RegisterUser user = registerUserLiveData.getValue();
        Log.d(TAG, "updateFormState: " + user.getIdentity());
        if (user == null) {
            Log.d(TAG, "updateFormState:is null");
            registerFormStateLiveData.setValue(new RegisterFormState(false));
        } else if (!isPhoneInvalid(user.getPhone())) {
            Log.d(TAG, "updateFormState: phone");
            registerFormStateLiveData.setValue(new RegisterFormState(R.string.ui_phone_invalid, null,null));
        } else if (!isPasswordInvalid(user.getPassword())) {
            Log.d(TAG, "updateFormState: password");
            registerFormStateLiveData.setValue(new RegisterFormState(null, R.string.ui_password_invalid,null));
        } else if (!isNameInvalid(user.getRealName())){
            Log.d(TAG, "updateFormState: name");
            registerFormStateLiveData.setValue(new RegisterFormState(null,null,R.string.ui_name_invalid));
        }
        else{
            Log.d(TAG, "updateFormState: no error");
            registerFormStateLiveData.setValue(new RegisterFormState(true));
        }
    }

    /**
     * Verify phone
     *
     * @param phoneNumber phoneNumber
     * @return 'true' if phoneNumber is valid, 'false' otherwise.
     */
    public boolean isPhoneInvalid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * Verify password
     *
     * @param password password
     * @return 'true' if password is valid, 'false' otherwise.
     */
    private boolean isPasswordInvalid(String password) {
        if (password == null) {
            return false;
        }
        return password.trim().length() >= 6;
    }

    private boolean isNameInvalid(String name) {
        if (name == null) {
            return false;
        }
        return name.trim().length() >= 1;
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
        @NonNull
        private final UserRepository userRepository;

        public Factory(Application application) {
            userRepository = ((MyApplication) application).getUserRepository();
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
                return (T) new RegisterViewModel(userRepository);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}

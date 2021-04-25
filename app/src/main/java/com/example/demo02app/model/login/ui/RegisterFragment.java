package com.example.demo02app.model.login.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    public interface RegisterListener {
        void onSuccess();
    }

    private static final String TAG = RegisterFragment.class.getName();
    private FragmentRegisterBinding binding;

    private RegisterViewModel registerViewModel;

    private boolean isPasswordVisible = false;

    @NonNull
    private RegisterListener registerListener;

    public RegisterFragment(@NonNull RegisterListener registerListener) {
        this.registerListener = registerListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RegisterViewModel.Factory factory = new RegisterViewModel.Factory(requireActivity().getApplication());
        registerViewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);

        binding.etPhone.addTextChangedListener(textWatcher);
        binding.etPassword.addTextChangedListener(textWatcher);

        // Observe RegisterFormState
        registerViewModel.getRegisterFormStateLiveData().observe(this, registerFormState -> {
            if (registerFormState == null) {
                binding.btnRegister.setEnabled(false);
                return;
            }
            binding.btnRegister.setEnabled(registerFormState.isInvalid());
            if (registerFormState.getPhoneInvalid() != null) {
                binding.etPhone.setError(getString(registerFormState.getPhoneInvalid()));
            }
            if (registerFormState.getPasswordInvalid() != null) {
                binding.etPassword.setError(getString(registerFormState.getPasswordInvalid()));
            }
        });

        // 观察注册结果
        registerViewModel.getRegisterResultLiveData().observe(this, registerResult -> {
            if (registerResult == null) {
                return;
            }
            if (registerResult.getError() != null) {
                Toast.makeText(requireContext(), getString(registerResult.getError()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), getString(R.string.ui_register_succeed), Toast.LENGTH_SHORT).show();
                // 注册成功
                // 自动登录
                registerListener.onSuccess();
            }
        });

        // 设置密码是否可见
        binding.etPassword.setOnTouchListener((v, event) -> {
            Drawable[] compoundDrawables = binding.etPassword.getCompoundDrawables();
            Drawable drawableEnd = compoundDrawables[2];
            if (drawableEnd == null) {
                return false;
            }
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            if (event.getX() > binding.etPassword.getWidth()
                    - binding.etPassword.getPaddingEnd()
                    - drawableEnd.getIntrinsicWidth()
                    && event.getX() < binding.etPassword.getWidth() - binding.etPassword.getPaddingEnd()
                    && event.getY() > binding.etPassword.getPaddingTop()
                    && event.getY() < binding.etPassword.getHeight() - binding.etPassword.getPaddingBottom()) {
                isPasswordVisible = !isPasswordVisible;
                Drawable drawable;
                if (isPasswordVisible) {
                    drawable = ContextCompat.getDrawable(requireContext(), R.mipmap.invisible);
                    // 设置密码可见
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    drawable = ContextCompat.getDrawable(requireContext(), R.mipmap.visible);
                    // 设置密码不可见
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (drawable == null) {
                    return false;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                binding.etPassword.setCompoundDrawables(null, null, drawable, null);
                return true;
            }
            return false;
        });

        binding.sIdentity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int identity = getResources().getIntArray(R.array.identity_value)[position];
                registerViewModel.getRegisterUserLiveData().getValue().setIdentity(identity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 注册按钮
        binding.btnRegister.setOnClickListener(v -> registerViewModel.register());
        binding.btnRegister.setEnabled(false);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            registerViewModel.updateFormState();
        }
    };
}
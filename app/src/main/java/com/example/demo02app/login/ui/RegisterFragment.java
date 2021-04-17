package com.example.demo02app.login.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentRegisterBinding;
import com.example.demo02app.login.data.RegisterResult;

public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getName();
    private FragmentRegisterBinding binding;

    private RegisterViewModel registerViewModel;

    private boolean isPasswordVisible = false;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_register, container, false);
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

        // 观察表单状态
        registerViewModel.getRegisterFormStateLiveData().observe(this, registerFormState -> {
            if (registerFormState == null) {
                return;
            }
            if (registerFormState.getPhoneInvalid() != null) {
                binding.etPhone.setError(getString(registerFormState.getPhoneInvalid()));
            }
            if (registerFormState.getPasswordInvalid() != null) {
                binding.etPassword.setError(getString(registerFormState.getPasswordInvalid()));
            }
        });

        // 观察注册结果
        registerViewModel.getRegisterResultLiveData().observe(this, registerResult -> {
            if (registerResult == null || registerResult.getResult() == null) {
                return;
            }
            switch (registerResult.getResult()) {
                default:
                    break;
                case RegisterResult.SUCCESS:
                    // 注册成功
                    Log.d(TAG, "registerResult:Success");
                    break;
                case RegisterResult.FAILURE:
                    // 注册失败
                    Log.d(TAG, "registerResult:Failure ");
                    break;
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

        binding.btnRegister.setOnClickListener(v -> registerViewModel.register());
    }
}
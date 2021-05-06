package com.example.demo02app.model.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getName();
    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: called");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: called");
        super.onActivityCreated(savedInstanceState);
        LoginViewModel.Factory factory = new LoginViewModel.Factory(requireActivity().getApplication());
        loginViewModel = new ViewModelProvider(requireActivity(), factory).get(LoginViewModel.class);

        binding.setLoginUserViewModel(loginViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // 添加TextWatcher。用户动态追踪输入状态
        binding.etUsername.addTextChangedListener(textWatcher);
        binding.etPassword.addTextChangedListener(textWatcher);

        // 绑定点击事件
        binding.etPassword.setOnEditorActionListener((v, actionId, event) -> {
            loginViewModel.login();
            return false;
        });


        binding.btnLogin.setOnClickListener(v -> loginViewModel.login());

//        // 观察
//        loginViewModel.getLoggedInUserLiveData().observe(getViewLifecycleOwner(), user -> {
//            if (user == null) {
//                Log.d(TAG, "onActivityCreated: user is null");
//            } else {
//                Log.d(TAG, "onActivityCreated: " + user.toString());
//            }
//        });

        // 观察表单状态
        loginViewModel.getLoginFormStateLiveData().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                binding.btnLogin.setEnabled(false);
                return;
            }
            // 是否禁用登录按钮
            binding.btnLogin.setEnabled(loginFormState.isValid());
            if (loginFormState.getUsernameError() != null) {
                binding.etUsername.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                binding.etPassword.setError(getString(loginFormState.getPasswordError()));
            }
        });

        binding.btnLogin.setEnabled(false);

        // 观察登录结果
        loginViewModel.getLoginResultLiveData().observe(getViewLifecycleOwner(), loginResult -> {
            Log.d(TAG, "onChanged: called");
            if (loginResult == null) {
                Log.d(TAG, "onChanged: loginResult is null");
                return;
            }
            if (loginResult.getError() != null) {
                Toast.makeText(requireContext(), getString(loginResult.getError()), Toast.LENGTH_SHORT).show();
                // 登录失败
            }else {
                // 登录成功，跳转到主界面
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged: " + (s == null ? "is null" : "not null"));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged: " + (s == null ? "is null" : "not null"));
        }

        @Override
        public void afterTextChanged(Editable s) {
//            loginViewModel.updateLoggedInUser(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());
            Log.d(TAG, "afterTextChanged: called");
            if (s.length() == 0) {
                Log.d(TAG, "afterTextChanged: ignore");
                return;
            }
            loginViewModel.updateFormState();
        }
    };
}
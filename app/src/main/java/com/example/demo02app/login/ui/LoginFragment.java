package com.example.demo02app.login.ui;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentLoginBinding;
import com.example.demo02app.login.data.LoginFormState;
import com.example.demo02app.login.data.LoginResult;

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
        loginViewModel.getLoginFormStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
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
            }
        });

        // 观察登录结果
        loginViewModel.getLoginResultLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                Log.d(TAG, "onChanged: called");
                if (loginResult == null || loginResult.getResult() == null) {
                    Log.d(TAG, "onChanged: loginResult is null");
                    return;
                }
                switch (loginResult.getResult()) {
                    case LoginResult.PASSWORD_ERROR:
                        Toast.makeText(requireContext(), "密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case LoginResult.USERNAME_NOT_EXIT:
                        Toast.makeText(requireContext(), "用户名错误", Toast.LENGTH_SHORT).show();
                        break;
                    case LoginResult.SUCCESS:
                        Toast.makeText(requireContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                        break;
                    case LoginResult.FAILURE:
                        Toast.makeText(requireContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            loginViewModel.updateLoggedInUser(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());
            loginViewModel.update();
        }
    };
}
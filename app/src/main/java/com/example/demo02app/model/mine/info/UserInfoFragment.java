package com.example.demo02app.model.mine.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentUserInfoBinding;
import com.example.demo02app.model.login.ui.LoginActivity;

public class UserInfoFragment extends Fragment {

    private static final String TAG = UserInfoFragment.class.getName();
    private UserInfoViewModel mViewModel;
    private FragmentUserInfoBinding binding;

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfoViewModel.Factory factory = new UserInfoViewModel.Factory(requireActivity().getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(UserInfoViewModel.class);

        mViewModel.getLogoutLiveDate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "onChanged: called");
                if (aBoolean == null) {
                    return;
                }
                if (aBoolean) {
                    Log.d(TAG, "onChanged: intent");
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: called ");
                mViewModel.logout();
            }
        });
    }
}
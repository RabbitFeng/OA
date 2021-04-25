package com.example.demo02app.model.mine.identity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.util.IdentityUtil;

public class IdentityConfigFragment extends Fragment {

    private IdentityViewModel mViewModel;

    public IdentityConfigFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_identity_config, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(IdentityViewModel.class);

        getView().findViewById(R.id.td_register_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getIdentityRegister().setIdentity(IdentityUtil.IDENTITY_EMPLOYEE);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, new IdentityRegisterFragment())
                        .addToBackStack("identity register")
                        .commit();
            }
        });

        getView().findViewById(R.id.td_register_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getIdentityRegister().setIdentity(IdentityUtil.IDENTITY_ADMIN);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, new IdentityRegisterFragment())
                        .addToBackStack("identity register")
                        .commit();
            }
        });
    }
}
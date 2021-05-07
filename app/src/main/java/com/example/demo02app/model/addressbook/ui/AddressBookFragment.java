package com.example.demo02app.model.addressbook.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentAddressBookBinding;

public class AddressBookFragment extends Fragment {

    private static final String TAG = AddressBookFragment.class.getName();
    private AddressBookViewModel mViewModel;

    private FragmentAddressBookBinding binding;

    private static final String USER_OTHER = "user_other";

    private AddressBookFragment() {
    }

    public AddressBookFragment forAddressBook(@NonNull String userOther) {
        AddressBookFragment fragment = new AddressBookFragment();
        Bundle data = new Bundle();
        data.putString(USER_OTHER, userOther);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address_book, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AddressBookViewModel.Factory factory = new AddressBookViewModel.Factory(requireActivity().getApplication(),
                requireArguments().getString(USER_OTHER));
        mViewModel = new ViewModelProvider(this, factory).get(AddressBookViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: called");
            }
        });


    }

}
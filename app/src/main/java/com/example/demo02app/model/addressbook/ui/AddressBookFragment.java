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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentAddressBookBinding;
import com.example.demo02app.model.addressbook.data.AddressBookItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

public class AddressBookFragment extends Fragment {

    private static final String TAG = AddressBookFragment.class.getName();
    private FragmentAddressBookBinding binding;
    private AddressBookViewModel mViewModel;
    private AddressBookAdapter addressBookAdapter;

    public static AddressBookFragment newInstance() {
        return new AddressBookFragment();
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
                ((MainActivity) requireActivity()).getCurrentUser().getUserId());
        mViewModel = new ViewModelProvider(requireActivity(), factory).get(AddressBookViewModel.class);
        binding.setLifecycleOwner(this);

        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新
                mViewModel.reLoad();

            }
        });

        addressBookAdapter = new AddressBookAdapter(null, new OnItemClickCallback<AddressBookItem>() {
            @Override
            public void onClick(@NonNull AddressBookItem addressBookItem) {
                Log.d(TAG, "onClick: " + addressBookItem.getUserId());
            }

            @Override
            public boolean onLongClick(@NonNull AddressBookItem addressBookItem) {
                return false;
            }
        });

        binding.rvAddressBook.setAdapter(addressBookAdapter);

        mViewModel.getAddressBookListLiveData().observe(getViewLifecycleOwner(), addressBooks -> {
            if (addressBooks == null) {
                return;
            }
            Log.d(TAG, "onActivityCreated: changed " + addressBooks.size());
            for (AddressBookItem addressBook : addressBooks) {
                Log.d(TAG, "onActivityCreated: " + addressBook);
            }
            addressBookAdapter.setList(addressBooks);
        });

    }

}
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

import com.example.demo02app.FragmentCallback;
import com.example.demo02app.MainActivity;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentAddressBookListBinding;
import com.example.demo02app.model.addressbook.model.AddressBookItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

public class AddressBookListFragment extends Fragment {

    private static final String TAG = AddressBookListFragment.class.getName();
    private FragmentAddressBookListBinding binding;
    private AddressBookListViewModel mViewModel;
    private AddressBookItemAdapter addressBookItemAdapter;

    @Nullable
    private FragmentCallback callback;

    private AddressBookListFragment(@Nullable FragmentCallback callback) {
        this.callback = callback;
    }

    public static AddressBookListFragment newInstance(@Nullable FragmentCallback callback) {
        return new AddressBookListFragment(callback);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callback != null) {
            callback.onFragmentNeedsFullScreen(false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address_book_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddressBookListViewModel.Factory factory = new AddressBookListViewModel.Factory(requireActivity().getApplication(),
                ((MainActivity) requireActivity()).getCurrentUser().getUserId());
        mViewModel = new ViewModelProvider(requireActivity(), factory).get(AddressBookListViewModel.class);
        binding.setLifecycleOwner(this);

        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新
                mViewModel.reLoad();
                binding.srlRefresh.setRefreshing(false);
            }
        });

        addressBookItemAdapter = new AddressBookItemAdapter(null, new OnItemClickCallback<AddressBookItem>() {
            @Override
            public void onClick(@NonNull AddressBookItem addressBookItem) {
                Log.d(TAG, "onClick: " + addressBookItem.getUserId());
//                ((MainActivity)requireActivity()).LoadFullScreenFragment(AddressBookFragment.forAddressBook(addressBookItem.getUserId()),
//                        "addressBook");
                if (callback != null) {
                    callback.onFragmentAddToBackStack(AddressBookFragment.forAddressBook(addressBookItem.getUserId()), "addressBook");
                    callback.onFragmentNeedsFullScreen(true);
                }
            }

            @Override
            public boolean onLongClick(View v, @NonNull AddressBookItem addressBookItem, int position) {
                return false;
            }

        });

        binding.rvAddressBook.setAdapter(addressBookItemAdapter);

        mViewModel.getAddressBookListLiveData().observe(getViewLifecycleOwner(), addressBooks -> {
            if (addressBooks == null) {
                return;
            }
            Log.d(TAG, "onActivityCreated: changed " + addressBooks.size());
            for (AddressBookItem addressBook : addressBooks) {
                Log.d(TAG, "onActivityCreated: " + addressBook);
            }
            addressBookItemAdapter.setList(addressBooks);
        });
    }


}
package com.example.demo02app.model.mine.ui;

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

import com.example.demo02app.FragmentCallback;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMineBinding;
import com.example.demo02app.model.mine.identity.IdentityConfigFragment;

public class MineFragment extends Fragment {

    private static final String TAG = MineFragment.class.getName();
    private FragmentCallback fragmentCallback;
    private FragmentMineBinding binding;
    private MineViewModel mViewModel;

    public MineFragment(FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MineViewModel.class);

        binding.tdIdentity.setOnClickListener(v -> {
            fragmentCallback.onFragmentNeedsFullScreen(true);
//            fragmentCallback.onFragmentAddToBackStack(new IdentityConfigFragment(),"identity");
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, new IdentityConfigFragment())
                    .addToBackStack("identity config0")
                    .commit();
        });
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called");
        fragmentCallback.onFragmentNeedsFullScreen(false);
        super.onStart();
    }

}
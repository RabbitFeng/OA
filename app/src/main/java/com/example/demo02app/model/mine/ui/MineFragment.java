package com.example.demo02app.model.mine.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.FragmentCallback;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMineBinding;
import com.example.demo02app.model.mine.info.UserInfoFragment;
import com.example.demo02app.model.schedule.ui.ScheduleListFragment;

public class MineFragment extends Fragment {

    private static final String TAG = MineFragment.class.getName();
    @Nullable
    private FragmentCallback callback;
    private FragmentMineBinding binding;
    private MineViewModel mViewModel;

    public static MineFragment newInstance(@Nullable FragmentCallback callback) {
        return new MineFragment(callback);
    }

    public MineFragment(@Nullable FragmentCallback callback) {
        this.callback = callback;
    }

    ActivityResultLauncher<String[]> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Log.d(TAG, "onActivityResult: set");

//                binding.ivProfile.setImageURI(result);
//                Glide.with(MineFragment.this)
//                        .load(result.toString())
//                        .into(binding.ivProfile);
                if (result != null) {
                    Log.d(TAG, "onActivityResult: toString " + result.toString());
                    Log.d(TAG, "onActivityResult: path " + result.getPath());
                }
            }
        });

//        voidActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.PickContact(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                Log.d(TAG, "onActivityResult: set");
//
//                binding.ivProfile.setImageURI(result);
//            }
//        });
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        if (callback != null) {
            callback.onFragmentNeedsFullScreen(false);
        }
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MineViewModel.Factory factory = new MineViewModel.Factory(requireActivity().getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(MineViewModel.class);

        binding.rlTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: called");
                if (callback != null) {
                    callback.onFragmentAddToBackStack(new UserInfoFragment(), "userInfo");
                    callback.onFragmentNeedsFullScreen(true);
                }
            }
        });

        binding.tdSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onFragmentAddToBackStack(new ScheduleListFragment(callback), "scheduleList");
                }
            }
        });

        binding.tdIdentity.setOnClickListener(v -> {
//            fragmentCallback.onFragmentNeedsFullScreen(true);
//            fragmentCallback.onFragmentAddToBackStack(new IdentityConfigFragment(),"identity");

//            requireActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fl_container, new IdentityConfigFragment())
//                    .addToBackStack("identity config0")
//                    .commit();
        });

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(new String[]{"image/*"});
//                voidActivityResultLauncher.launch(null);
//
//                registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback<Uri>() {
//                    @Override
//                    public void onActivityResult(Uri result) {
//
//                    }
//                }).launch(new String[]{"image/*"});

            }
        });
    }


}
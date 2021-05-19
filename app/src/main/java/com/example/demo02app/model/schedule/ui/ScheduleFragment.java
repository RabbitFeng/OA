package com.example.demo02app.model.schedule.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentScheduleBinding;

public class ScheduleFragment extends Fragment {

    private static final String TAG = ScheduleFragment.class.getName();
    private ScheduleViewModel viewModel;

    private FragmentScheduleBinding binding;

    public static final String TIME_IN_MILLIONS = "time_in_millions";

    public static ScheduleFragment newInstance(long timeInMillions) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(TIME_IN_MILLIONS, timeInMillions);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, null, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScheduleViewModel.Factory factory = new ScheduleViewModel.Factory(requireActivity().getApplication(),
                requireArguments().getLong(TIME_IN_MILLIONS));
        viewModel = new ViewModelProvider(this, factory).get(ScheduleViewModel.class);

        binding.setViewModel(viewModel);

        binding.tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_save) {
                    Log.d(TAG, "onMenuItemClick: called");
                    viewModel.save();
                }
                return false;
            }
        });

    }

}
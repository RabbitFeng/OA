package com.example.demo02app.model.schedule.ui;

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
import com.example.demo02app.databinding.FragmentScheduleListBinding;
import com.example.demo02app.util.CalendarUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.Map;

public class ScheduleListFragment extends Fragment {

    private static final String TAG = ScheduleListFragment.class.getName();

    private static long selectCalendar;

    private FragmentScheduleListBinding binding;
    private ScheduleListViewModel mViewModel;

    @Nullable
    private FragmentCallback callback;

    public ScheduleListFragment(@Nullable FragmentCallback callback) {
        this.callback = callback;
    }

    public static ScheduleListFragment newInstance(@Nullable FragmentCallback callback) {
        return new ScheduleListFragment(callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d(TAG, "onViewCreated: called");

        if (callback != null) {
            callback.onFragmentNeedsFullScreen(true);
        }
        ScheduleListViewModel.Factory factory = new ScheduleListViewModel.Factory(requireActivity().getApplication());
        mViewModel = new ViewModelProvider(requireActivity(), factory).get(ScheduleListViewModel.class);

        binding.cv.setOnCalendarLongClickListener(new CalendarView.OnCalendarLongClickListener() {
            @Override
            public void onCalendarLongClickOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarLongClick(Calendar calendar) {
                if (callback != null) {
                    callback.onFragmentAddToBackStack(ScheduleFragment.newInstance(calendar.getTimeInMillis()), "schedule");
                    callback.onFragmentNeedsFullScreen(true);
                }
            }
        });

        mViewModel.getScheduleItemListLiveData().observe(getViewLifecycleOwner(), scheduleItems -> {
            if (scheduleItems != null) {
                Map<String, Calendar> schemeMap = CalendarUtil.getSchemeMap(scheduleItems);
                binding.cv.setSchemeDate(schemeMap);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }
}
package com.example.demo02app.model.meeting.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMeetingBinding;
import com.example.demo02app.model.meeting.data.MeetingItem;

import java.util.ArrayList;
import java.util.List;

public class MeetingFragment extends Fragment {

    private MeetingViewModel mViewModel;
    private FragmentMeetingBinding binding;
    private MeetingAdapter adapter;

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);

        adapter = new MeetingAdapter();
        binding.rv.setAdapter(adapter);

        List<MeetingItem> meetingItems = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            meetingItems.add(new MeetingItem("会议主题" + i, "18:00", "18:30", getString(R.string.hint_address)));
        }

        adapter.setMeetingItemList(meetingItems);

        // 下拉刷新
        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<MeetingItem> meetingItems = new ArrayList<>();
                for (int i = 30; i < 50; i++) {
                    meetingItems.add(new MeetingItem("会议主题" + i, "18:00", "18:30", getString(R.string.hint_address)));
                }

                adapter.setMeetingItemList(meetingItems);
                binding.srlRefresh.setRefreshing(false);
            }
        });
    }

}
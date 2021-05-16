package com.example.demo02app.model.meeting.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMeetingListBinding;
import com.example.demo02app.model.meeting.data.model.MeetingItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

public class MeetingListFragment extends Fragment {

    private static final String TAG = MeetingListFragment.class.getName();
    private MeetingListViewModel viewModel;
    private FragmentMeetingListBinding binding;
    private MeetingItemAdapter adapter;

    public static MeetingListFragment newInstance() {
        return new MeetingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeetingListViewModel.Factory factory = new MeetingListViewModel.Factory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(getViewModelStore(), factory).get(MeetingListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_list, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取用户权限
        int userIdentity = ((MyApplication) requireActivity().getApplication()).getUserIdentity();

//        if (userIdentity == IdentityUtil.IDENTITY_ADMIN) {
//            // 若为管理员权限
//            binding.tb.inflateMenu(R.menu.tb_menu_meeting);
//        }
        binding.tb.inflateMenu(R.menu.tb_menu_meeting);

        binding.tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: ");
                return true;
            }
        });

        adapter = new MeetingItemAdapter(null, new OnItemClickCallback<MeetingItem>() {
            @Override
            public void onClick(@NonNull MeetingItem meetingItem) {
//                ((MainActivity)requireActivity()).onFragmentAddToBackStack(new );
            }

            @Override
            public boolean onLongClick(@NonNull MeetingItem meetingItem) {
                return false;
            }
        });
        binding.rv.setAdapter(adapter);

        // 下拉刷新
        binding.srlRefresh.setOnRefreshListener(() -> {
            binding.srlRefresh.setRefreshing(false);
        });

        viewModel.getMeetingItemLiveData().observe(requireActivity(), meetingItems -> {
            Log.d(TAG, "onChanged: called");
            if (meetingItems != null) {
                adapter.setList(meetingItems);
            }
        });
    }
}
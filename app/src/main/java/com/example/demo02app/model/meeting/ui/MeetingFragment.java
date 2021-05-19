package com.example.demo02app.model.meeting.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMeetingBinding;
import com.example.demo02app.model.meeting.data.entity.MeetingDetail;
import com.example.demo02app.model.meeting.data.entity.MeetingDetailView;

public class MeetingFragment extends Fragment {

    private static final String TAG = MeetingFragment.class.getName();
    private MeetingViewModel mViewModel;
    private FragmentMeetingBinding binding;

    public static final String KEY_ID = "meeting_id";

    private MeetingFragment() {
    }

    public static MeetingFragment newInstance(int id) {
        Bundle data = new Bundle();
        data.putInt(KEY_ID, id);
        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MeetingViewModel.Factory factory = new MeetingViewModel.Factory(requireActivity().getApplication(),
                requireArguments().getInt(KEY_ID));

        mViewModel = new ViewModelProvider(this, factory).get(MeetingViewModel.class);

//        binding.setViewModel(mViewModel);

        mViewModel.getMeetingDetailLiveData().observe(getViewLifecycleOwner(), new Observer<MeetingDetail>() {
            @Override
            public void onChanged(MeetingDetail meetingDetail) {
                if (meetingDetail != null) {
                    binding.setViewModel(mViewModel);
                    Log.d(TAG, "onChanged: " + meetingDetail.toString());
                }
                Log.d(TAG, "onChanged: called " + (meetingDetail == null ? "null" : "not null"));
            }
        });

        mViewModel.getMeetingDetailViewLiveData().observe(getViewLifecycleOwner(), new Observer<MeetingDetailView>() {
            @Override
            public void onChanged(MeetingDetailView meetingDetailView) {
                Log.d(TAG, "onChanged meetingDetailView: called");
                if (meetingDetailView == null) {
                    return;
                }
                Log.d(TAG, "onChanged: MeetingDetailView "+meetingDetailView.toString());
            }
        });
    }
}
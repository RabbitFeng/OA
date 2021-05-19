package com.example.demo02app.model.meeting.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMeetingPublishBinding;
import com.example.demo02app.model.meeting.data.MeetingPublishFormState;
import com.example.demo02app.model.meeting.data.MeetingPublishResult;
import com.example.demo02app.util.view.DateTimePicker;

public class MeetingPublishFragment extends Fragment {

    private static final String TAG = MeetingPublishFragment.class.getName();
    private MeetingPublishViewModel viewModel;

    private FragmentMeetingPublishBinding binding;

    DateTimePicker dateTimePicker;

    public static MeetingPublishFragment newInstance() {
        return new MeetingPublishFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_publish, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MeetingPublishViewModel.class);

        binding.setViewModel(viewModel);

        viewModel.getMeetingFormStateLiveData().observe(getViewLifecycleOwner(), new Observer<MeetingPublishFormState>() {
            @Override
            public void onChanged(MeetingPublishFormState meetingPublishFormState) {
                if (meetingPublishFormState != null) {
//                    if (meetingFormState.getError() != null) {
//                        Toast.makeText(requireContext(), getString(meetingFormState.getError()), Toast.LENGTH_SHORT).show();
//                    }
                    binding.btnPublish.setEnabled(meetingPublishFormState.isValid());
                } else {
                    binding.btnPublish.setEnabled(false);
                }
            }
        });

        viewModel.getPublishResultLiveData().observe(getViewLifecycleOwner(), new Observer<MeetingPublishResult>() {
            @Override
            public void onChanged(MeetingPublishResult meetingPublishResult) {
                if (meetingPublishResult == null) {
                    return;
                }
                if (meetingPublishResult.getError() == null) {
                    getParentFragmentManager().popBackStack();
                }else {
                    Toast.makeText(requireContext(), getString(meetingPublishResult.getError()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 绑定点击事件
        binding.etTitle.addTextChangedListener(textWatcher);
        binding.etAddress.addTextChangedListener(textWatcher);
        binding.tvBeginTime.setOnClickListener(v -> {
            if (dateTimePicker == null) {
                dateTimePicker = new DateTimePicker(requireContext());
            }
            dateTimePicker.show(timeMillions -> viewModel.updateBeginTime(timeMillions));

        });

        binding.tvEndTime.setOnClickListener(v -> {
            if (dateTimePicker == null) {
                dateTimePicker = new DateTimePicker(requireContext());
            }
            dateTimePicker.show(timeMillions -> viewModel.updateEndTime(timeMillions));
        });

        binding.btnPublish.setOnClickListener(v -> viewModel.publish());

        binding.btnPublish.setEnabled(false);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            viewModel.updateFormState();
        }
    };
}
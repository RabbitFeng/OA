package com.example.demo02app.model.notice.ui;

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
import com.example.demo02app.databinding.FragmentNoticePublishBinding;
import com.example.demo02app.model.notice.NoticePublishResult;
import com.example.demo02app.model.notice.data.NoticePublishFormState;

public class NoticePublishFragment extends Fragment {

    private NoticePublishViewModel viewModel;
    private FragmentNoticePublishBinding binding;

    public static NoticePublishFragment newInstance() {
        return new NoticePublishFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice_publish, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NoticePublishViewModel.Factory factory = new NoticePublishViewModel.Factory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(NoticePublishViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getFormStateLiveData().observe(getViewLifecycleOwner(), new Observer<NoticePublishFormState>() {
            @Override
            public void onChanged(NoticePublishFormState noticePublishFormState) {
                if (noticePublishFormState == null) {
                    return;
                }
                if (noticePublishFormState.getError() != null) {
                    Toast.makeText(requireContext(), getString(noticePublishFormState.getError()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<NoticePublishResult>() {
            @Override
            public void onChanged(NoticePublishResult noticePublishResult) {
                if (noticePublishResult == null) {
                    return;
                }
                if (noticePublishResult.getError() != null) {
                    Toast.makeText(requireContext(), getString(noticePublishResult.getError()), Toast.LENGTH_SHORT).show();
                } else {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        binding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.publish();
            }
        });
    }

    final TextWatcher textWatcher = new TextWatcher() {
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
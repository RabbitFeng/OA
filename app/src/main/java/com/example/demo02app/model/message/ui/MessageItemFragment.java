package com.example.demo02app.model.message.ui;

import android.content.Intent;
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

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMessageBinding;
import com.example.demo02app.model.chat.ui.ChatActivity;
import com.example.demo02app.model.message.data.MessageItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.ArrayList;
import java.util.List;

public class MessageItemFragment extends Fragment {
    private static final String TAG = MessageItemFragment.class.getName();
    private FragmentMessageBinding binding;
    private MessageItemViewModel mViewModel;
    private MessageItemAdapter adapter;

    public static MessageItemFragment newInstance() {
        return new MessageItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageItemViewModel.class);

        Log.d(TAG, "onActivityCreated: 关闭toolbar");
        adapter = new MessageItemAdapter(null, new OnItemClickCallback<MessageItem>() {
            @Override
            public void onClick(@NonNull MessageItem messageItem) {
                // 跳转到ChatActivity
                Intent intent = new Intent(requireActivity(), ChatActivity.class);
                intent.putExtra(getString(R.string.bundle_message_from), messageItem.getFrom());
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(@NonNull MessageItem messageItem) {
                return false;
            }
        });

        binding.rv.setAdapter(adapter);

        List<MessageItem> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(new MessageItem(i + "", null, "董" + i, "这是一条消息", "2020-01-02"));
        }
        adapter.setMessagesList(items);

        binding.srlRefresh.setOnRefreshListener(() -> {
            List<MessageItem> items2 = new ArrayList<>();
            for (int i = 30; i < 50; i++) {
                items2.add(new MessageItem(i + "", null, "董" + i, "这是一条消息", "2020-01-02"));
            }
            adapter.setMessagesList(items2);
            binding.srlRefresh.setRefreshing(false);
        });
    }
}
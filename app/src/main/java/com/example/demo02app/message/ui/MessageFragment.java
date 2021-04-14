package com.example.demo02app.message.ui;

import android.os.Bundle;
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
import com.example.demo02app.message.data.MessageItem;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    private FragmentMessageBinding binding;
    private MessageViewModel mViewModel;
    private MessageAdapter adapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
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
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        adapter = new MessageAdapter();

        binding.rv.setAdapter(adapter);

        List<MessageItem> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(new MessageItem(null, "董" + i, "这是一条消息", "2020-01-02"));
        }
        adapter.setMessagesList(items);

        binding.srlRefresh.setOnRefreshListener(() -> {
            List<MessageItem> items2 = new ArrayList<>();
            for (int i = 30; i < 50; i++) {
                items2.add(new MessageItem(null, "董" + i, "这是一条消息", "2020-01-02"));
            }
            adapter.setMessagesList(items2);
            binding.srlRefresh.setRefreshing(false);
        });
    }
}
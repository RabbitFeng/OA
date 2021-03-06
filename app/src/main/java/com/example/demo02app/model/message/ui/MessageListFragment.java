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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMessageListBinding;
import com.example.demo02app.model.chat.ui.ChatActivity;
import com.example.demo02app.model.message.data.MessageItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.List;

public class MessageListFragment extends Fragment {
    private static final String TAG = MessageListFragment.class.getName();
    private FragmentMessageListBinding binding;
    private MessageListViewModel mViewModel;
    private MessageItemAdapter adapter;

    public static MessageListFragment newInstance() {
        return new MessageListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_list, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MessageListViewModel.Factory factory = new MessageListViewModel.Factory(requireActivity().getApplication());
        mViewModel = new ViewModelProvider(requireActivity(), factory).get(MessageListViewModel.class);

        Log.d(TAG, "onActivityCreated: 关闭toolbar");

        adapter = new MessageItemAdapter(null, new OnItemClickCallback<MessageItem>() {
            @Override
            public void onClick(@NonNull MessageItem messageItem) {
                // 跳转到ChatActivity
                Intent intent = new Intent(requireActivity(), ChatActivity.class);
                Bundle data = new Bundle();
                data.putString(getString(R.string.bundle_user_other), messageItem.getUserOther());
                intent.putExtra(getString(R.string.bundle_name), data);
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View v, @NonNull MessageItem messageItem, int position) {
                return false;
            }
        });

        binding.rv.setAdapter(adapter);

        mViewModel.getMessageItemListLiveData().observe(requireActivity(), new Observer<List<MessageItem>>() {
            @Override
            public void onChanged(List<MessageItem> messageItems) {
                Log.d(TAG, "onChanged: called");
                adapter.setList(messageItems);
            }
        });
//

//        List<MessageItem> items = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            items.add(new MessageItem(i + "", null, "董" + i, "这是一条消息", DateTimeUtil.getCurrentTimeStamp()));
//        }
//        adapter.setMessagesList(items);

        binding.srlRefresh.setOnRefreshListener(() -> {
//            List<MessageItem> items2 = new ArrayList<>();
//            for (int i = 30; i < 50; i++) {
//                items2.add(new MessageItem(i + "", null, "董" + i, "这是一条消息", DateTimeUtil.getCurrentTimeStamp()));
//            }
//            adapter.setMessagesList(items2);
            binding.srlRefresh.setRefreshing(false);
        });
    }
}
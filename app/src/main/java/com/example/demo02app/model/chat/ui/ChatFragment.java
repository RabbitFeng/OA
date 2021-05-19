package com.example.demo02app.model.chat.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentChatBinding;
import com.example.demo02app.model.chat.data.ChatMessageItem;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.Objects;

public class ChatFragment extends Fragment {

    private static final String TAG = ChatFragment.class.getName();

    private ChatViewModel viewModel;

    private FragmentChatBinding binding;

    private ChatMessageItemAdapter adapter;

    private final static String USER_OTHER = "user_other";

    /**
     * RecyclerView回滚距离
     * 软键盘弹出/隐藏时,RecyclerView的布局大小改变,使得视图底部展示当前最后一条记录,手动滚动RecyclerView
     */
    private int rvRollBack;

//    private JWebSocketClientService socketClientService;

//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d(TAG, "onServiceConnected: called");
//            JWebSocketClientService.JWebSocketClientBinder binder =
//                    (JWebSocketClientService.JWebSocketClientBinder) service;
//            socketClientService = binder.getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d(TAG, "onServiceDisconnected: called");
//        }
//    };

    public static ChatFragment forUserChat(String userOther) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle data = new Bundle();
        data.putString(USER_OTHER, userOther);
        chatFragment.setArguments(data);
        Log.d(TAG, "forUserChat: new instance with userOther:" + userOther);
        return chatFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ChatViewModel.Factory factory = new ChatViewModel.Factory(requireActivity().getApplication(),
//                requireArguments().getString(USER_OTHER));
//        viewModel = new ViewModelProvider(requireActivity(), factory).get(ChatViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        binding.setLifecycleOwner(requireActivity());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        inflater.inflate(R.menu.tb_menu_message, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: called");

            }
        });

//        binding.tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Log.d(TAG, "onMenuItemClick: called");
//                return false;
//            }
//        });

        adapter = new ChatMessageItemAdapter(null, new OnItemClickCallback<ChatMessageItem>() {
            @Override
            public void onClick(@NonNull ChatMessageItem chatMessageItem) {
                // 隐藏软键盘
                binding.viewInput.etContent.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(binding.viewInput.etContent.getWindowToken(), 0);
            }

            @Override
            public boolean onLongClick(View v, @NonNull ChatMessageItem chatMessageItem, int position) {
                return false;
            }

        });
        binding.rvMessage.setAdapter(adapter);

        // 对ChatMessageList注册观察
        viewModel.getChatMessageListLiveData().observe(requireActivity(), chatMessageItems -> {
            if (chatMessageItems == null) {
                return;
            }
            Log.d(TAG, "onChanged: called " + chatMessageItems.size());
            adapter.setList(chatMessageItems);
            binding.rvMessage.scrollToPosition(chatMessageItems.size() - 1);

            for (ChatMessageItem chatMessageItem : chatMessageItems) {
                Log.d(TAG, chatMessageItem.toString());
            }
        });

        // 监听视图变化，使得软键盘弹出时，RecyclerView能够跟随最后一项
        binding.rvMessage.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            EditText输入会触发该监听器
            if (binding.rvMessage.getLayoutManager() instanceof LinearLayoutManager
                    && ((LinearLayoutManager) binding.rvMessage.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
                Log.d(TAG, "onLayoutChange " + " " + left + " " + top + " " + right + " " + bottom + " " + oldLeft + " " + oldTop + " " + oldRight + " " + oldBottom);
                // 滚动范围
                Log.d(TAG, "onLayoutChange: computeVerticalScrollRange:" + binding.rvMessage.computeVerticalScrollRange());
                // 滚动距离偏移量
                Log.d(TAG, "onLayoutChange: computeVerticalScrollOffset:" + binding.rvMessage.computeVerticalScrollOffset());
                // 当前可见滚动范围
                Log.d(TAG, "onLayoutChange: computeVerticalScrollExtent:" + binding.rvMessage.computeVerticalScrollExtent());

                int viewHeight = binding.rvMessage.getHeight();
                if (binding.rvMessage.computeVerticalScrollRange() < viewHeight) {
                    // 若rv的滚动范围小于当前rv的高度则不滚动
                    return;
                }
                int dif = oldBottom - bottom;
                if (dif > 0) {
                    // 软键盘弹出，rv滚动
                    int vRange = binding.rvMessage.computeVerticalScrollRange();
                    int vOffset = binding.rvMessage.computeVerticalScrollOffset();
                    int vExtent = binding.rvMessage.computeVerticalScrollExtent();

                    // 到最后一条Item的距离
                    int vToBottom = vRange - vOffset - vExtent;
                    Log.d(TAG, "onLayoutChange: toBottom:" + vToBottom);
                    Log.d(TAG, "onLayoutChange: dif:" + dif);
                    // 记录回滚距离
                    rvRollBack = Math.min(vToBottom - dif, dif);
                    Log.d(TAG, "onLayoutChange: roll " + dif);
                    binding.rvMessage.scrollBy(0, dif);
                } else if (dif < 0) {
                    // 软键盘隐藏，rv回滚
                    Log.d(TAG, "onLayoutChange: rollback" + (-rvRollBack));
                    binding.rvMessage.scrollBy(0, -rvRollBack);
                } else {
                    Log.d(TAG, "onLayoutChange: do Nothing ");
                }
            }
        });

        // 隐藏软键盘
        binding.rvMessage.setOnTouchListener((v, event) -> {
            Log.d(TAG, "onTouch: called");
            binding.viewInput.etContent.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.viewInput.etContent.getWindowToken(), 0);
            return false;
        });

        binding.viewInput.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                viewModel.update(s.toString());
                Log.d(TAG, "afterTextChanged: called s:" + binding.viewInput.etContent.getText().toString());
                String content = binding.viewInput.etContent.getText().toString();
                viewModel.update(content);
                binding.viewInput.btnSend.setEnabled(!content.isEmpty());
            }
        });

        // 发送消息
        binding.viewInput.btnSend.setOnClickListener(v -> {
            String content = binding.viewInput.etContent.getText().toString();
            if (!content.isEmpty()) {
                Log.d(TAG, "onActivityCreated: " + Objects.requireNonNull(viewModel.getChatMessageMutableLiveData().getValue()).toString());
                ((ChatActivity) requireActivity()).sendMessage(viewModel.getChatMessageMutableLiveData().getValue());
                // 发送后清空输入框
                binding.viewInput.etContent.getText().clear();
                // 滚动到最后一条消息
                binding.rvMessage.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }
}
package com.example.demo02app.model.chat.ui;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.demo02app.service.JWebSocketClientService;

public class ChatFragment extends Fragment {

    private static final String TAG = ChatFragment.class.getName();

    private ChatViewModel mViewModel;

    private FragmentChatBinding binding;

    private int rvRollBack;

    private JWebSocketClientService socketClientService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: called");
            JWebSocketClientService.JWebSocketClientBinder binder =
                    (JWebSocketClientService.JWebSocketClientBinder) service;
            socketClientService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: called");
        }
    };

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ChatViewModel.class);

        // 监听视图变化，使得软键盘弹出时，RecyclerView能够跟随最后一项
        binding.rvMessage.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (binding.rvMessage.getLayoutManager() instanceof LinearLayoutManager
                    && ((LinearLayoutManager) binding.rvMessage.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
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

                    int vToBottom = vRange - vOffset - vExtent;
                    Log.d(TAG, "onLayoutChange: toBottom:" + vToBottom);
                    Log.d(TAG, "onLayoutChange: dif:" + dif);
                    // 记录回滚距离
                    rvRollBack = Math.min(vToBottom - dif, dif);
                    Log.d(TAG, "onLayoutChange: roll " + dif);
                    binding.rvMessage.scrollBy(0, dif);
                } else {
                    // 软键盘隐藏，rv回滚
                    Log.d(TAG, "onLayoutChange: rollback" + (-rvRollBack));
                    binding.rvMessage.scrollBy(0, -rvRollBack);
                }
            }
        });

        // 隐藏软键盘
        binding.rvMessage.setOnTouchListener((v, event) -> {
            binding.viewInput.etContent.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.viewInput.etContent.getWindowToken(), 0);
            return false;
        });

        binding.viewInput.btnSend.setOnClickListener(v -> {
            String content = binding.viewInput.etContent.getText().toString();
            if (!content.isEmpty()) {
//                socketClientService.sendMessage();
            }
        });
    }
}
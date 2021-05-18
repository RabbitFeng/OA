package com.example.demo02app.model.meeting.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.FragmentCallback;
import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentMeetingListBinding;
import com.example.demo02app.model.meeting.data.model.MeetingItem;
import com.example.demo02app.util.IdentityUtil;
import com.example.demo02app.util.adapter.OnItemClickCallback;

public class MeetingListFragment extends Fragment {

    private static final String TAG = MeetingListFragment.class.getName();
    private MeetingListViewModel viewModel;
    private FragmentMeetingListBinding binding;
    private MeetingItemAdapter adapter;

    @Nullable
    private FragmentCallback callback;

    private MeetingListFragment(@Nullable FragmentCallback callback) {
        this.callback = callback;
    }

    public static MeetingListFragment newInstance(@Nullable FragmentCallback callback) {
        return new MeetingListFragment(callback);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeetingListViewModel.Factory factory = new MeetingListViewModel.Factory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(MeetingListViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callback != null) {
            callback.onFragmentNeedsFullScreen(false);
        }
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

        if (userIdentity == IdentityUtil.IDENTITY_ADMIN) {
            // 若为管理员权限
            binding.tb.inflateMenu(R.menu.tb_menu_meeting);
            binding.tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onMenuItemClick: called");
                    if (item.getItemId() == R.id.action_publish_meeting) {
                        if (callback != null) {
                            callback.onFragmentAddToBackStack(MeetingPublishFragment.newInstance(), "meetingPublish");
                            callback.onFragmentNeedsFullScreen(true);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }

        adapter = new MeetingItemAdapter(null, new OnItemClickCallback<MeetingItem>() {
            @Override
            public void onClick(@NonNull MeetingItem meetingItem) {
                if (callback != null) {
                    callback.onFragmentAddToBackStack(MeetingFragment.newInstance(meetingItem.getId()), "meeting");
                    callback.onFragmentNeedsFullScreen(true);
                }
            }

            @Override
            public boolean onLongClick(View v, @NonNull MeetingItem meetingItem, int position) {
                if (userIdentity == IdentityUtil.IDENTITY_ADMIN) {
                    PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.pop_menu_delete, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(item -> {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_delete) {
                            Log.d(TAG, "onMenuItemClick: called");
                            new AlertDialog.Builder(requireContext()).setTitle("确定删除？")
                                    .setPositiveButton(R.string.ui_positive, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            viewModel.delete(meetingItem.getId());
                                        }
                                    })
                                    .setNegativeButton(R.string.ui_cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        return true;
                    });
                    popupMenu.show();
                }
                return true;
            }
        });


        binding.rv.setAdapter(adapter);

//        binding.rv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.add(0x01, 0x01, 0, R.string.ui_delete);
//            }
//        });

        // 下拉刷新
        binding.srlRefresh.setOnRefreshListener(() -> {
            viewModel.reLoad();
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
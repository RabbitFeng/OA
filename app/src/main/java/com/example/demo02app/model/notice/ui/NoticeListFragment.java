package com.example.demo02app.model.notice.ui;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo02app.FragmentCallback;
import com.example.demo02app.MyApplication;
import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentNoticeListBinding;
import com.example.demo02app.model.notice.data.model.NoticeItem;
import com.example.demo02app.util.IdentityUtil;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.List;

public class NoticeListFragment extends Fragment {
    private static final String TAG = NoticeListFragment.class.getName();
    private FragmentNoticeListBinding binding;
    private NoticeListViewModel viewModel;

    private NoticeItemAdapter adapter;

    @NonNull
    private FragmentCallback callback;

    public NoticeListFragment(@NonNull FragmentCallback callback) {
        this.callback = callback;
    }

    public static NoticeListFragment newInstance(@NonNull FragmentCallback callback) {
        return new NoticeListFragment(callback);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoticeListViewModel.Factory factory = new NoticeListViewModel.Factory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(NoticeListViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();
        callback.onFragmentNeedsFullScreen(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice_list, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        int userIdentity = ((MyApplication) requireActivity().getApplication()).getUserIdentity();

        if (userIdentity == IdentityUtil.IDENTITY_ADMIN) {
            // 管理员权限
            binding.tb.inflateMenu(R.menu.tb_menu_notice);
            binding.tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onMenuItemClick: called");
                    if (item.getItemId() == R.id.action_publish_notice) {
                        callback.onFragmentAddToBackStack(NoticePublishFragment.newInstance(), "noticePublish");
                        callback.onFragmentNeedsFullScreen(true);
                        return true;
                    }
                    return false;
                }
            });
        }


        adapter = new NoticeItemAdapter(null, new OnItemClickCallback<NoticeItem>() {
            @Override
            public void onClick(@NonNull NoticeItem noticeItem) {
            }

            @Override
            public boolean onLongClick(View v, @NonNull NoticeItem noticeItem, int position) {
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
                                            viewModel.delete(noticeItem.getId());
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
        binding.rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        // 下拉刷新
        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.reload();
                binding.srlRefresh.setRefreshing(false);
            }
        });

        viewModel.getNoticeListLiveData().observe(getViewLifecycleOwner(), new Observer<List<NoticeItem>>() {
            @Override
            public void onChanged(List<NoticeItem> noticeItems) {
                Log.d(TAG, "onChanged: called");
                Log.d(TAG, "onChanged: "+noticeItems.toString());
                if (noticeItems != null) {
                    adapter.setList(noticeItems);
                }
            }
        });
    }

}
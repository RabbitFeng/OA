package com.example.demo02app.notice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo02app.R;
import com.example.demo02app.databinding.FragmentNoticeBinding;
import com.example.demo02app.notice.data.NoticeItem;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    private FragmentNoticeBinding binding;
    private NoticeViewModel mViewModel;

    private NoticeAdapter adapter;

    public static NoticeFragment newInstance() {
        return new NoticeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);

        adapter = new NoticeAdapter();
        binding.rv.setAdapter(adapter);
        binding.rv.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));

        List<NoticeItem> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(new NoticeItem("董"+i, getString(R.string.large_text), "2020-01-02"));
        }

        adapter.setNoticeItemList(items);


        // 下拉刷新
        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<NoticeItem> items = new ArrayList<>();
                for (int i = 30; i < 50; i++) {
                    items.add(new NoticeItem("董"+i, getString(R.string.large_text), "2020-01-02"));
                }

                adapter.setNoticeItemList(items);
                binding.srlRefresh.setRefreshing(false);
            }
        });
    }

}
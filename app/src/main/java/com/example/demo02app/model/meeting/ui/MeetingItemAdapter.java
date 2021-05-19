package com.example.demo02app.model.meeting.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemMeetingBinding;
import com.example.demo02app.model.meeting.data.model.MeetingItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeetingItemAdapter extends AbstractBindingAdapter<MeetingItem,ItemMeetingBinding> {
    private static final String TAG = MeetingItemAdapter.class.getName();

    public MeetingItemAdapter(@Nullable List<MeetingItem> list, @Nullable OnItemClickCallback<MeetingItem> onItemClickCallback) {
        super(list, onItemClickCallback);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_meeting;
    }

    @Override
    public void onBind(@NonNull BindingHolder<ItemMeetingBinding> holder, @NotNull MeetingItem meetingItem, int position) {
        holder.getBinding().setMeetingItem(meetingItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<MeetingItem> list) {
        setMeetingItemList(list);
    }

    public void setMeetingItemList(List<MeetingItem> meetingItemList) {
        Log.d(TAG, "setMeetingItemList: called");
        if (list == null) {
            this.list = meetingItemList;
            notifyItemRangeChanged(0, meetingItemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return meetingItemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }
            });
            this.list = meetingItemList;
            result.dispatchUpdatesTo(this);
        }
    }

}


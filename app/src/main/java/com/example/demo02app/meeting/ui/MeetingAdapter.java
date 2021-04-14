package com.example.demo02app.meeting.ui;

import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemMeetingBinding;
import com.example.demo02app.meeting.data.MeetingItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;

import java.util.List;

public class MeetingAdapter extends AbstractBindingAdapter<MeetingItem,ItemMeetingBinding> {
    private static final String TAG = MeetingAdapter.class.getName();

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_meeting;
    }

    @Override
    public void onBind(ViewHolder<ItemMeetingBinding> holder, MeetingItem meetingItem, int position) {
        holder.getBinding().setMeetingItem(meetingItem);
        holder.getBinding().executePendingBindings();
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

//    private static final String TAG = MeetingAdapter.class.getName();
//    private List<? extends MeetingItem> mMeetingItemList;
//
//    public void setMeetingItemList(List<MeetingItem> meetingItemList) {
//        Log.d(TAG, "setMeetingItemList: called");
//        if (mMeetingItemList == null) {
//            this.mMeetingItemList = meetingItemList;
//            notifyItemRangeChanged(0, meetingItemList.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return mMeetingItemList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return meetingItemList.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                    return false;
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    return false;
//                }
//            });
//            this.mMeetingItemList = meetingItemList;
//            result.dispatchUpdatesTo(this);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemMeetingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.item_meeting, parent, false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.binding.setMeetingItem(mMeetingItemList.get(position));
//        holder.binding.executePendingBindings();
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMeetingItemList == null ? 0 : mMeetingItemList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final ItemMeetingBinding binding;
//
//        public ViewHolder(ItemMeetingBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//    }
}


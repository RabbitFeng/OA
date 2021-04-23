package com.example.demo02app.model.message.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemMessageBinding;
import com.example.demo02app.model.message.data.MessageItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;

import java.util.List;

public class MessageAdapter extends AbstractBindingAdapter<MessageItem, ItemMessageBinding> {


    private static final String TAG = MessageAdapter.class.getName();

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message;
    }

    @Override
    public void onBind(ViewHolder<ItemMessageBinding> holder, MessageItem item, int position) {
        holder.getBinding().setMessageItem(item);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<MessageItem> list) {
        setMessagesList(list);
    }

    public void setMessagesList(List<MessageItem> messagesList) {
        Log.d(TAG, "setMessagesList: called");
        if (list == null) {
            this.list = messagesList;
            notifyItemRangeChanged(0, messagesList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return messagesList.size();
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
            this.list = messagesList;
            result.dispatchUpdatesTo(this);
        }
    }

//    private List<? extends MessageItem> mMessagesList;
//
//    public MessageAdapter() {
//    }
//
//    public void setMessagesList(List<MessageItem> messagesList) {
//        Log.d(TAG, "setMessagesList: called");
//        if (mMessagesList == null) {
//            this.mMessagesList = messagesList;
//            notifyItemRangeChanged(0, messagesList.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return mMessagesList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return messagesList.size();
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
//            this.mMessagesList = messagesList;
//            result.dispatchUpdatesTo(this);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.item_message, parent, false);
//
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: called");
//        holder.binding.setMessageItem(mMessagesList.get(position));
//        holder.binding.executePendingBindings();
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMessagesList == null ? 0 : mMessagesList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final ItemMessageBinding binding;
//
//        public ViewHolder(ItemMessageBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//    }
}

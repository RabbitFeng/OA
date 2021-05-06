package com.example.demo02app.model.message.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemMessageBinding;
import com.example.demo02app.model.message.data.MessageItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends AbstractBindingAdapter<MessageItem, ItemMessageBinding> {

    private static final String TAG = MessageAdapter.class.getName();

    public MessageAdapter() {
        super(null, null);
    }

    public MessageAdapter(@Nullable List<MessageItem> list, @Nullable OnItemClickCallback<MessageItem> onItemClickCallback) {
        super(list, onItemClickCallback);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message;
    }

    @Override
    public void onBind(@NonNull @NotNull ViewHolder<ItemMessageBinding> holder, @NotNull MessageItem item, int position) {
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

}

package com.example.demo02app.model.chat.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemChatMessageBinding;
import com.example.demo02app.model.chat.entity.ChatMessageItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatMessageItemAdapter extends AbstractBindingAdapter<ChatMessageItem, com.example.demo02app.databinding.ItemChatMessageBinding> {

    public ChatMessageItemAdapter() {
        super(null,null);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_chat_message;
    }

    @Override
    public void onBind(@NotNull @NonNull BindingHolder<ItemChatMessageBinding> holder, @NotNull @NonNull ChatMessageItem chatMessageItem, int position) {
        holder.getBinding().setChatMessageItem(chatMessageItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<ChatMessageItem> chatMessageItemList) {
        if (this.list == null) {
            this.list = chatMessageItemList;
            notifyItemRangeChanged(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return chatMessageItemList.size();
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
            this.list = chatMessageItemList;
            result.dispatchUpdatesTo(this);
        }
    }
}

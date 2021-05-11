package com.example.demo02app.model.chat.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemChatMessageReceiverBinding;
import com.example.demo02app.databinding.ItemChatMessageSendBinding;
import com.example.demo02app.model.chat.entity.ChatMessageItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.List;

public class ChatMessageItemAdapter extends AbstractBindingAdapter<ChatMessageItem, ViewDataBinding> {
    public static final int VIEW_SEND = 0x01;
    public static final int VIEW_RECEIVER = 0x02;


    public ChatMessageItemAdapter(@Nullable List<ChatMessageItem> list, @Nullable OnItemClickCallback<ChatMessageItem> onItemClickCallback) {
        super(list, onItemClickCallback);
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            default:
            case VIEW_SEND:
                return R.layout.item_chat_message_send;
            case VIEW_RECEIVER:
                return R.layout.item_chat_message_receiver;
        }
    }

    @Override
    public void onBind(@NonNull BindingHolder<ViewDataBinding> holder, @NonNull ChatMessageItem chatMessageItem, int position) {
        ViewDataBinding binding = holder.getBinding();
        if (binding instanceof ItemChatMessageSendBinding) {
            ((ItemChatMessageSendBinding) binding).setChatMessageItem(chatMessageItem);
        } else if (binding instanceof ItemChatMessageReceiverBinding) {
            ((ItemChatMessageReceiverBinding) binding).setChatMessageItem(chatMessageItem);
        }
//        binding.setVariable(BR.chatMessageItem,chatMessageItem);
        binding.executePendingBindings();

    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isSend()) {
            return VIEW_SEND;
        } else {
            return VIEW_RECEIVER;
        }
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
                    return list.get(oldItemPosition).getId() == chatMessageItemList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return list.get(oldItemPosition).getTime() == chatMessageItemList.get(newItemPosition).getTime()
                            && list.get(oldItemPosition).getContent().equals(chatMessageItemList.get(newItemPosition).getContent());
                }
            });
            this.list = chatMessageItemList;
            result.dispatchUpdatesTo(this);
        }
    }
}

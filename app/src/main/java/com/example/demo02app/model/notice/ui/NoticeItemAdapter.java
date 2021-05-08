package com.example.demo02app.model.notice.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemNoticeBinding;
import com.example.demo02app.model.notice.data.NoticeItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoticeItemAdapter extends AbstractBindingAdapter<NoticeItem, ItemNoticeBinding> {
    public NoticeItemAdapter() {
        super(null, null);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_notice;
    }

    @Override
    public void onBind(@NonNull @NotNull BindingHolder<ItemNoticeBinding> holder, @NotNull NoticeItem noticeItem, int position) {
        holder.getBinding().setNoticeItem(noticeItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<NoticeItem> list) {
        setNoticeItemList(list);
    }

    public void setNoticeItemList(List<NoticeItem> noticeItemList) {
        if (list == null) {
            this.list = noticeItemList;
            notifyItemRangeChanged(0, noticeItemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return noticeItemList.size();
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
            this.list = noticeItemList;
            result.dispatchUpdatesTo(this);
        }
    }

}

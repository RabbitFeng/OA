package com.example.demo02app.notice.ui;

import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemNoticeBinding;
import com.example.demo02app.notice.data.NoticeItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;

import java.util.List;

public class NoticeAdapter extends AbstractBindingAdapter<NoticeItem, ItemNoticeBinding> {
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_notice;
    }

    @Override
    public void onBind(ViewHolder<ItemNoticeBinding> holder, NoticeItem noticeItem, int position) {
        holder.getBinding().setNoticeItem(noticeItem);
        holder.getBinding().executePendingBindings();
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

//    private List<? extends NoticeItem> mNoticeItemsList;
//
//    public void setNoticeItemsList(List<? extends NoticeItem> mNoticeItemsList) {
//        this.mNoticeItemsList = mNoticeItemsList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemNoticeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.item_notice,parent,false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.binding.setNoticeItem(mNoticeItemsList.get(position));
//        holder.binding.executePendingBindings();
//    }
//
//    @Override
//    public int getItemCount() {
//        return mNoticeItemsList ==null?0: mNoticeItemsList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final ItemNoticeBinding binding;
//
//        public ViewHolder(ItemNoticeBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
}

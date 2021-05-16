package com.example.demo02app.model.addressbook.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.demo02app.R;
import com.example.demo02app.databinding.ItemAddressBookBinding;
import com.example.demo02app.model.addressbook.model.AddressBookItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.List;

public class AddressBookItemAdapter extends AbstractBindingAdapter<AddressBookItem, ItemAddressBookBinding> {

    public AddressBookItemAdapter(@Nullable List<AddressBookItem> list, @Nullable OnItemClickCallback<AddressBookItem> onItemClickCallback) {
        super(list, onItemClickCallback);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_address_book;
    }

    @Override
    public void onBind(@NonNull BindingHolder<ItemAddressBookBinding> holder, @NonNull AddressBookItem addressBookItem, int position) {
        holder.getBinding().setAddressBookItem(addressBookItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<AddressBookItem> addressBookItemList) {
        if (this.list == null) {
            this.list = addressBookItemList;
            notifyItemMoved(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return addressBookItemList.size();
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
            this.list = addressBookItemList;
            result.dispatchUpdatesTo(this);
        }
//        notifyDataSetChanged();
    }
}

package com.example.demo02app.model.adressbook.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo02app.databinding.ItemAddressBookBinding;
import com.example.demo02app.model.adressbook.data.AddressBookItem;
import com.example.demo02app.util.adapter.AbstractBindingAdapter;
import com.example.demo02app.util.adapter.OnItemClickCallback;

import java.util.List;

public class AddressBookAdapter extends AbstractBindingAdapter<AddressBookItem, ItemAddressBookBinding> {

    public AddressBookAdapter(@Nullable List<AddressBookItem> list, @Nullable OnItemClickCallback<AddressBookItem> onItemClickCallback) {
        super(list, onItemClickCallback);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public void onBind(@NonNull ViewHolder<ItemAddressBookBinding> holder, @NonNull AddressBookItem addressBookItem, int position) {
        holder.getBinding().setAddressBookItem(addressBookItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void setList(@NonNull List<AddressBookItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}

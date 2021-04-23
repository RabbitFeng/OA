package com.example.demo02app.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class AbstractBindingAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<AbstractBindingAdapter.ViewHolder<B>> {
    /**
     * 数据源
     */
    protected List<T> list;

    /**
     * 获取布局id
     *
     * @param viewType view类型
     * @return 布局id
     */
    @LayoutRes
    public abstract int getLayoutId(int viewType);

    /**
     * 绑定
     *
     * @param holder   ViewHolder
     * @param t        数据
     * @param position 位置
     */
    public abstract void onBind(@NonNull ViewHolder<B> holder, @NonNull T t, int position);

    /**
     * 更新列表
     * @param list list of T
     */
    public abstract void setList(@NonNull List<T> list);

    @NonNull
    @Override
    public ViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(viewType), parent, false);
        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<B> holder, int position) {
        onBind(holder, list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        @NonNull
        protected B binding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        public B getBinding() {
            return binding;
        }
    }
}

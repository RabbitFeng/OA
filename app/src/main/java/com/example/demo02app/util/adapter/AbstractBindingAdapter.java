package com.example.demo02app.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class AbstractBindingAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<AbstractBindingAdapter.BindingHolder<B>> {
    /**
     * 数据源
     */
    protected List<T> list;

    /**
     * item点击事件回调
     */
    private OnItemClickCallback<T> onItemClickCallback;

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
    public abstract void onBind(@NonNull BindingHolder<B> holder, @NonNull T t, int position);

    /**
     * 更新列表
     *
     * @param list list of T
     */
    public abstract void setList(@NonNull List<T> list);

    public AbstractBindingAdapter(@Nullable List<T> list, @Nullable OnItemClickCallback<T> onItemClickCallback) {
        this.list = list;
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public BindingHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(viewType), parent, false);
        return new BindingHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder<B> holder, int position) {
        onBind(holder, list.get(position), position);
        // 绑定点击事件
        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (onItemClickCallback == null) {
                return;
            }
            onItemClickCallback.onClick(list.get(position));
        });

        holder.getBinding().getRoot().setOnLongClickListener(v -> {
            if (onItemClickCallback == null) {
                return false;
            }
            return onItemClickCallback.onLongClick(holder.getBinding().getRoot(), list.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class BindingHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        @NonNull
        protected B binding;

        public BindingHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        public B getBinding() {
            return binding;
        }
    }
}

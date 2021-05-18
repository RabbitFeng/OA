package com.example.demo02app.util.adapter;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnItemClickCallback<T> {
    /**
     * 点击事件
     *
     * @param t 数据
     */
    void onClick(@NonNull T t);

    /**
     * 长按事件
     *
     * @param t 数据
     * @return 是否处理
     */
    boolean onLongClick(View v, @NonNull T t, int position);
}

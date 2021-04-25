package com.example.demo02app.util.adapter;

import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class SpinnerBindingAdapter {
    @BindingAdapter("onSelect")
    public static <T extends Spinner> void onSelect(T view, String[] entity, int position) {
    }

    @InverseBindingAdapter(attribute = "onSelect")
    public static <T extends Spinner> void setSelect(T view){

    }

}

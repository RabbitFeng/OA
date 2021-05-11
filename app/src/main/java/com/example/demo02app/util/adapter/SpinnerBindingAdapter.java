package com.example.demo02app.util.adapter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class SpinnerBindingAdapter {
    private static final String TAG = SpinnerBindingAdapter.class.getName();

    @BindingAdapter(value = "select")
    public static void selectItem(Spinner spinner, String str) {
        Log.d(TAG, "selectItem: called");
        int count = spinner.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            Object item = spinner.getAdapter().getItem(i);
            if (item.equals(str)) {
                spinner.setSelection(i);
                return;
            }
        }
    }

    @InverseBindingAdapter(attribute = "select", event = "selectAttrChanged")
    public static <T extends Spinner> String getSelectItem(T spinner) {
        Log.d(TAG, "getSelectItem: called");
        return spinner.getSelectedItem().toString();
    }

    @BindingAdapter(value = {"selectAttrChanged"}, requireAll = false)
    public static void setListener(Spinner spinner,
                                   final InverseBindingListener listener) {
        Log.d(TAG, "setListener: called");
        AdapterView.OnItemSelectedListener onItemSelectedListener;
        onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: called");
                if (listener != null) {
                    listener.onChange();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        if (spinner.getOnItemSelectedListener() == null) {
            spinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }
}

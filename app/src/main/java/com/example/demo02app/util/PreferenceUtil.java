package com.example.demo02app.util;

import android.content.Context;

import java.lang.ref.WeakReference;

public class PreferenceUtil {
    public WeakReference<Context> context;

    public PreferenceUtil(WeakReference<Context> context) {
        this.context = context;
    }
}

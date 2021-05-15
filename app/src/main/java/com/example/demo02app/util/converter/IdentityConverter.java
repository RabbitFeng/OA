package com.example.demo02app.util.converter;

import android.util.Log;

import androidx.databinding.InverseMethod;

import com.example.demo02app.util.IdentityUtil;

public class IdentityConverter {

    private static final String TAG = IdentityConverter.class.getName();

    @InverseMethod("convertToInteger")
    public static String convertToString(int identity) {
        Log.d(TAG, "convertToString: called");
        return IdentityUtil.getIdentityName(identity);
    }

    public static int convertToInteger(String identityName) {
        Log.d(TAG, "convertToInteger: ");
        return IdentityUtil.getIdentity(identityName);
    }
}

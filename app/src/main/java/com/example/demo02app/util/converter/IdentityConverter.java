package com.example.demo02app.util.converter;

import androidx.databinding.InverseMethod;

import com.example.demo02app.util.IdentityUtil;

public class IdentityConverter {
    @InverseMethod("convertToInteger")
    public static String convertToString(int identity) {
        return IdentityUtil.getIdentityName(identity);
    }

    public static int convertToInteger(String identityName) {
        return IdentityUtil.getIdentity(identityName);
    }
}

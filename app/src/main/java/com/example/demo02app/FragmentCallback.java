package com.example.demo02app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentCallback {
    void onFragmentNeedsFullScreen(boolean isNeed);

    /**
     * 子Fragment入栈
     * @param fragment 子fragment
     */
    void onFragmentAddToBackStack(@NonNull Fragment fragment, @NonNull String name);
}

package com.example.demo02app.login.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.demo02app.R;
import com.example.demo02app.login.MyCallback;
import com.example.demo02app.login.data.model.LoggedInUser;
import com.example.demo02app.util.OkHttpUtil;
import com.example.demo02app.util.PermissionsUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;

public class LoginDataSource {
    @NonNull
    private final Context appContext;

    public LoginDataSource(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public void login(String username, String password, Callback callback) {
        Map<String, String> hashMap = new HashMap<String, String>() {{
            put(getString(R.string.param_username), username);
            put(getString(R.string.param_password), password);
        }};
        OkHttpUtil.postAsync(getString(R.string.url_login), hashMap, callback);
    }

    public void register(String username, String password, MyCallback<LoggedInUser> callback) {
    }

    public void addUserCacheLocal(LoggedInUser user) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_username), user.getUsername())
                .putString(getString(R.string.pref_password), user.getPassword())
                .putBoolean(getString(R.string.pref_logout), false)
                .putInt(getString(R.string.pref_permissions), user.getPermission())
                .apply();
    }

    public LoggedInUser getUserCacheLocal() {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(getString(R.string.pref_login_key), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(getString(R.string.pref_username), "");
        String password = sharedPreferences.getString(getString(R.string.pref_password), "");
        Integer permission = sharedPreferences.getInt(getString(R.string.pref_permissions), PermissionsUtil.PERMISSION_EMPLOYEE);
        boolean isLogout = sharedPreferences.getBoolean(getString(R.string.pref_logout), true);
        return new LoggedInUser(username, password, permission, isLogout);
    }

    private String getString(@StringRes int res) {
        return appContext.getString(res);
    }

}

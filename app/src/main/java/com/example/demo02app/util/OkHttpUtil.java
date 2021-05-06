package com.example.demo02app.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    /**
     *
     * @param urlStr url字符串
     * @param map    映射
     */
    public static Call post(@NonNull String urlStr, @Nullable Map<String, String> map) {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5000, TimeUnit.MILLISECONDS).build();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = map.get(key);
                builder.add(key, value != null ? value : "");
            }
        }
        Request request = new Request.Builder()
                .url(urlStr)
                .put(builder.build())
                .build();
        return client.newCall(request);
    }

    public static Response postSyn(@NonNull String urlStr, Map<String, String> map, @NonNull Callback callback) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5000, TimeUnit.MILLISECONDS).build();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = map.get(key);
                builder.add(key, value != null ? value : "");
            }
        }
        Request request = new Request.Builder()
                .url(urlStr)
                .put(builder.build())
                .build();

        return client.newCall(request).execute();
    }
}

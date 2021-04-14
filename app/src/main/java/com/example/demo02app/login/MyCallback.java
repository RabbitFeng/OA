package com.example.demo02app.login;

public interface MyCallback<T> {
    void onSuccess(T t);
    void onFailure();
}

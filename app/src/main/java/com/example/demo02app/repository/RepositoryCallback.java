package com.example.demo02app.repository;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> t);
}

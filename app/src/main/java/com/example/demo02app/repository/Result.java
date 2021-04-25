package com.example.demo02app.repository;

public abstract class Result<T> {
    public static final int RESULT_SUCCESS = 0x01;
    public static final int RESULT_FAILURE = 0x10;

    private Result() {
    }

    public static final class Success<T> extends Result<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Error<T> extends Result<T> {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}

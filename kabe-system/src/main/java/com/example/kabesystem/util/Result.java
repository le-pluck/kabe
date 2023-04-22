package com.example.kabesystem.util;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;

@CacheConfig
@AllArgsConstructor
public class Result<T> {
    public int code;
    public String message;
    public T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "请求成功", data);
    }

    public static <T> Result<T> postSuccess(T data) {
        return new Result<>(201, "该请求已成功，并因此创建了一个新的资源。", data);
    }

    public static <T> Result<T> response(int code, String message, T data) {
        return new Result<>(code, message, data);
    }
    
    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> failure(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    @Override
    public String toString() {
        return "{" +
                "    \"code\": " + code + ",\n" +
                "    \"message\": \"" + message + "\",\n" +
                "    \"data\": " + data + "\n" +
                '}';
    }
}
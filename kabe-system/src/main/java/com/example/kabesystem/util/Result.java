package com.example.kabesystem.util;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;

@CacheConfig
@AllArgsConstructor
public class Result<T> {
    public int code;
    public String msg;
    public T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "请求成功", data);
    }

    public static <T> Result<T> postSuccess(T data) {
        return new Result<>(201, "该请求已成功，并因此创建了一个新的资源。", data);
    }

    public static <T> Result<T> success(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> failure(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    @Override
    public String toString() {
        return "{" +
                "    \"code\": " + code + ",\n" +
                "    \"msg\": \"" + msg + "\",\n" +
                "    \"data\": " + data + "\n" +
                '}';
    }
}
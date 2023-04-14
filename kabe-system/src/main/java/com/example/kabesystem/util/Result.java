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
        return new Result<>(200, "success", data);
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
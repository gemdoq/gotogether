package com.example.gotogether.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private ResultCode resultCode;
    private T result;

    public static <T> Response<T> error(T result) {
        return new Response<>(ResultCode.ERROR, result);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>(ResultCode.SUCCESS, result);
    }

}

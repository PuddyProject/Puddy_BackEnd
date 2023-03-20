package com.team.puddy.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T data;

    public static <T> Response<T> success() {
        return new Response<T>("SUCCESS", null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>("SUCCESS", data);
    }

    public static Response<Void> error(String resultCode) {
        return new Response<Void>(resultCode, null);
    }

    public String toStream() {
        if (data == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + data + "\"," +
                "}";
    }
}
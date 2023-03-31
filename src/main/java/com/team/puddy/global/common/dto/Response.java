package com.team.puddy.global.common.dto;

import com.team.puddy.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T data;

    //데이터 없이 응답 성공
    public static <T> Response<T> success() {
        return new Response<T>("SUCCESS", null);
    }
    //데이터 포함해서 응답 성공
    public static <T> Response<T> success(T data) {
        return new Response<T>("SUCCESS", data);
    }

    //에러
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
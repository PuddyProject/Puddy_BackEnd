package com.team.puddy.global.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Entity Not Found"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "Invalid Method"),
    JSON_WRITE_ERROR(HttpStatus.BAD_REQUEST, "Json Write Error"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    SERVLET_ERROR(HttpStatus.BAD_REQUEST, "Servlet Error"),

    // JWT
    JWT_EXCEPTION(HttpStatus.BAD_REQUEST, "Jwt Exception"),
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "권한이 없습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 토큰입니다"),

    //USER
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST,"중복된 아이디입니다"),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST,"중복된 이메일입니다" ),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"가입된 유저가 아닙니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"유효하지 않은 패스워드입니다.");

    private final HttpStatus status;
    private final String message;


}
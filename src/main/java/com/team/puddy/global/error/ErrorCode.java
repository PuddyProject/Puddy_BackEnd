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
    INVALID_ERROR(HttpStatus.BAD_REQUEST, "Invalid Error"),
    IMAGE_PROCESSING_ERROR(HttpStatus.BAD_REQUEST,"이미지 처리중 오류가 발생했습니다."),


    // JWT
    JWT_EXCEPTION(HttpStatus.BAD_REQUEST, "Jwt Exception"),
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "권한이 없습니다."),

    TOKEN_VERIFY_FAIL(HttpStatus.BAD_REQUEST, "토큰 검증에 실패했습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 토큰입니다"),

    //USER
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST, "중복된 아이디입니다"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "가입된 유저가 아닙니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효하지 않은 패스워드입니다."),
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "업데이트할 이미지가 없습니다."),
    UNAUTHORIZED_OPERATION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    //QUESTION
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 문제입니다."),
    NOT_THE_WRITER(HttpStatus.FORBIDDEN, "작성자만 채택할 수 있습니다."),

    //EXPERT
    EXPERT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 전문가입니다."),
    NOT_THE_EXPERT(HttpStatus.BAD_REQUEST,"해당 유저는 전문가가 아닙니다."),
    DUPLICATE_EXPERT(HttpStatus.BAD_REQUEST, "이미 등록된 전문가 유저입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    PET_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저는 펫을 가지지 않았습니다"),

    //ARTICLE
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");


    private final HttpStatus status;
    private final String message;


}
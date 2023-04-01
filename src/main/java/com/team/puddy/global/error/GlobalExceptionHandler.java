package com.team.puddy.global.error;

import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.EntityNotFoundException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.error.exception.user.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //서비스 로직 예외를 처리하기 위한 핸들러 메서드
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<?> businessException(final BusinessException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

    //@유효성 검증 실패 예외를 처리하기 위한 핸들러 메서드
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> invalidException(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(e.getMessage()));
    }

    //엔티티 찾지 못하는 예외를 처리하기 위한 핸들러 메서드
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> notFoundException(final NotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }
    //중복시 발생하는 예외 처리 핸들러
    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<?> duplicateException(final DuplicateException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }

}
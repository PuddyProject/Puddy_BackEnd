package com.team.puddy.global.error;

import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
    protected Response<Void> invalidException(final MethodArgumentNotValidException e) {
        return Response.error(ErrorCode.INVALID_INPUT_VALUE.getMessage());
    }


    //todo
}
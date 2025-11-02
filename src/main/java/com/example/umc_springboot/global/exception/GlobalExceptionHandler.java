package com.example.umc_springboot.global.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import com.example.umc_springboot.global.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j

// 전역 예외처리 해주는 어노테이션
// 모든 @RestController에서 발생한 예외를 잡아서 처리해줌
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomException(CustomException e){
        BaseErrorCode baseErrorCode = e.getErrorCode();
        log.error("Custom 오류 발생: {}", e.getErrorCode());
        return ResponseEntity
                .status(baseErrorCode.getStatus()) // ResponseEntity에 Http status 설정
                .body(GlobalResponse.error(baseErrorCode.getCode(), baseErrorCode.getMessage()));
    }

    // 비즈니스 로직 예외 처리
    // 이는 서비스/도메인 로직에서 사전 조건이 틀렸을 때 빠르게 400 Bad Request로 응답하기 위한 핸들러
    // 서비스 메서드의 입력값이 유효 범위를 벗어남 등의 경우
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<?>> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse.error("400", e.getMessage()));
    }

    // MethodArgumentNotValidException: 함수 인자의 @Valid를 할 때, 맞지 않는 형식이 있을 때 발생하는 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse.error("400", e.getMessage()));
    }

    // Exception 최후의 보루 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<?>> handleException(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalResponse.error("500", e.getMessage()));
    }


}

package com.example.umc_springboot.domain.user.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    EMAIL_OVERLAPPED("USER_001", "이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_OUT_OF_FORM("USER_002", "비밀번호가 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_ID_IS_NULL("USER_003", "전달된 user_id가 NULL입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USER_004", "사용자를 찾지 못했습니다.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

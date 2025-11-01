package com.example.umc_springboot.domain.userStoreMission.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserStoreMissionException implements BaseErrorCode {
    USM_UNFILLED("USM_001", "해당 사용자의 미션 데이터가 완전하지 않습니다.", HttpStatus.BAD_REQUEST);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

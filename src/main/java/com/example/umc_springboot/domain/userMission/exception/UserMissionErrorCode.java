package com.example.umc_springboot.domain.userMission.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserMissionErrorCode implements BaseErrorCode {
    USER_STORE_MISSION_UNFILLED("USM_001", "해당 사용자의 미션 데이터가 완전하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_CHALLENGED("USM_002", "해당 사용자는 이미 미션에 도전했습니다.", HttpStatus.CONFLICT);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

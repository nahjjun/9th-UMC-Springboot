package com.example.umc_springboot.domain.userMission.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserMissionErrorCode implements BaseErrorCode {
    USER_STORE_MISSION_UNFILLED("USM_001", "해당 사용자의 미션 데이터가 완전하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_CHALLENGED("USM_002", "해당 사용자는 이미 미션에 도전했습니다.", HttpStatus.CONFLICT),
    USER_CHALLENGING_MISSION_EMPTY("USM_003", "해당 사용자가 도전하고 있는 미션이 없습니다.", HttpStatus.NOT_FOUND),
    USER_COMPLETED_MISSION_EMPTY("USM_004", "해당 사용자가 완료한 미션이 없습니다.", HttpStatus.NOT_FOUND),
    USER_FAILED_MISSION_EMPTY("USM_005", "해당 사용자가 실패한 미션이 없습니다.", HttpStatus.NOT_FOUND),
    USER_EXPIRED_MISSION_EMPTY("USM_006", "해당 사용자가 신청한 미션들 중 만료된 미션이 없습니다.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

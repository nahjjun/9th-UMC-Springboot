package com.example.umc_springboot.domain.storeMission.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum StoreMissionErrorCode implements BaseErrorCode {
    STORE_MISSION_NOT_FOUND("SM_001", "가게가 등록한 미션 기록을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    STORE_MISSION_INACTIVE("SM_002", "가게가 등록한 미션이 비활성화 상태입니다.", HttpStatus.FORBIDDEN),;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

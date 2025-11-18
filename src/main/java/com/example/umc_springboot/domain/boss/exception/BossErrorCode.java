package com.example.umc_springboot.domain.boss.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BossErrorCode implements BaseErrorCode {
    BOSS_NOT_FOUND("B_001", "사장님 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

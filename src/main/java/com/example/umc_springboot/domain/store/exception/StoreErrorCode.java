package com.example.umc_springboot.domain.store.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum StoreErrorCode implements BaseErrorCode {
    STORE_NOT_FOUND("S_001", "가게를 찾지 못했습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

package com.example.umc_springboot.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalSuccessCode {
    SUCCESS_CODE("G_001", "요청 성공", HttpStatus.OK);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

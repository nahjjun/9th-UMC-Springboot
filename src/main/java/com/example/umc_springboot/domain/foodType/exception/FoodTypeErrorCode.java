package com.example.umc_springboot.domain.foodType.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FoodTypeErrorCode implements BaseErrorCode {
    FOOD_TYPE_ERROR("FT_001", "해당 FOOD TYPE을 DB에서 찾지 못했습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

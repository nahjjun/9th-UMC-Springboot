package com.example.umc_springboot.domain.review.exception;

import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode{
    SEARCH_REVIEW_WITH_NULL_STORE_ID("REVIEW_001", "해당 가게의 리뷰를 찾으려 했지만, 전달된 storeId가 null입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

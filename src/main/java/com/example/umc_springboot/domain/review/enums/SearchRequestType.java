package com.example.umc_springboot.domain.review.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum SearchRequestType {
    @Schema(description = "동을 기준으로 검색 요청")
    DONG, 
    @Schema(description = "별점을 기준으로 검색 요청")
    STAR, 
    @Schema(description = "동과 별점을 기준으로 검색 요청")
    DONG_STAR,
    @Schema(description = "해당 가게에 등록된 리뷰들을 검색 요청")
    STORE,
    @Schema(description = "해당 가게에 등록된 리뷰들 중 별점을 기준으로 검색 요청")
    STORE_STAR,
}

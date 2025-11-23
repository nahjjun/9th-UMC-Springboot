package com.example.umc_springboot.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record ReviewListResDto(
        List<ReviewResDto> reviewList,
        Integer listSize,       // 검색 목록 개수
        Integer totalPage,      // 총 페이지 개수
        Long totalElements,     // 총 검색 개수
        Boolean isFirst,        // 첫번째 페이지인가?
        Boolean isLast          // 마지막 페이지인가?
) {
}

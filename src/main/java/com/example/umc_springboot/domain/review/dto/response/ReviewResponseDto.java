package com.example.umc_springboot.domain.review.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
// 특정 가게의 리뷰
public class ReviewResponseDto {
    // 리뷰 고유 id
    private final Long reviewId;
    // 리뷰를 단 가게 이름
    private final String storeName;
    // 해당 리뷰를 작성한 사람의 닉네임
    private final String nickname;
    // 해당 리뷰의 별점
    private final Integer star;
    // 해당 리뷰 본문 내용
    private final String body;
    // 해당 리뷰에 달린 리뷰 사진들 url 리스트
    private final List<String> photoUrls;
    // 리뷰를 남긴 날짜
    private final LocalDate reviewDate;
}

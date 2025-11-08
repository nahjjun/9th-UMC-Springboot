package com.example.umc_springboot.domain.review;

import com.example.umc_springboot.UmcSpringBootApplication;
import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.dto.response.ReviewResponseDto;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.domain.review.service.ReviewService;
import com.example.umc_springboot.domain.user.dto.request.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest(classes = com.example.umc_springboot.UmcSpringBootApplication.class)
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 검색 테스트 - 동 기준")
    void getAllReviews_ByDong() {
        // given
        Dong dong = Dong.INSADONG;                // 검색할 동 (예: 인사동)
        int star = 0;                             // 최소 별점
        Long storeId = null;                      // 특정 가게 ID (null이면 전체)
        SearchRequestType type = SearchRequestType.DONG; // 검색 타입 (동 기준)
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")); // 최신순 10개

        // when
        List<ReviewResponseDto> results = reviewService.searchReviews(
                dong, star, storeId, type, pageable
        );

        // then
        System.out.println("✅ 검색된 리뷰 수: " + results.size());
        results.forEach(r -> {
            System.out.println("리뷰 ID: " + r.getReviewId());
            System.out.println("가게명: " + r.getStoreName());
            System.out.println("작성자: " + r.getNickname());
            System.out.println("별점: " + r.getStar());
            System.out.println("사진 수: " + r.getPhotoUrls().size());
            System.out.println("----------------------------------");
        });
    }

}
package com.example.umc_springboot.domain.review.repository.querydsl;

import com.example.umc_springboot.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;

import java.util.List;


// 리뷰에서 사용할 QueryDsl 인터페이스
public interface ReviewQueryDsl {
    /**
     * 리뷰 검색 API
     *
     */
    List<Review> searchReview(Predicate predicate);
}

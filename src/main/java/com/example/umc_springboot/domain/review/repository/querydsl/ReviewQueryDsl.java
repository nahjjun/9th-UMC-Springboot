package com.example.umc_springboot.domain.review.repository.querydsl;

import com.example.umc_springboot.domain.review.dto.response.ReviewResDto;
import com.example.umc_springboot.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


// 리뷰에서 사용할 QueryDsl 인터페이스
public interface ReviewQueryDsl {
    /**
     * 리뷰 검색 API (페이징 없음)
     * @param predicate : where 절 조건 객체. BooleanBuilder는 Predicate를 구현한 구현체이다.
     */
    List<Review> searchReviews(Predicate predicate);
    /**
     * 리뷰 검색 API(페이징 있음). ReviewResponseDto를 애초에 매핑해서 반환해준다.
     * @param predicate : where 절 조건 객체. BooleanBuilder는 Predicate를 구현한 구현체이다.
     * @param pageable : pageable 객체
     */
    Page<ReviewResDto> searchReviews(Predicate predicate, Pageable pageable);
}

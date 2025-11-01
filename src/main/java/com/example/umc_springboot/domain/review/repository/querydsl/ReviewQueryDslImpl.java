package com.example.umc_springboot.domain.review.repository.querydsl;

import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.review.repository.ReviewRepository;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl {
    private final ReviewRepository reviewRepository;
    private final EntityManager em;

    /**
     * 리뷰 검색 API
     */
    @Override
    public List<Review> searchReview(Predicate predicate) {
        return null;
    }

}

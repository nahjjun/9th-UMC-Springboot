package com.example.umc_springboot.domain.review.service;

import com.example.umc_springboot.domain.review.dto.request.ReviewSearchRequestDto;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * 들어온 검색 기준들을 기반으로 사용자가 작성한 리뷰를 검색해서 반환하는 함수
     * @param dto : 리뷰 데이터를 어떤 기준으로 검색해서 반환할 것인지가 들어있는 dto
     * @return : dto에 들어있는 검색 기준으로 검색된 Review의 List
     */
//    public List<Review> searchReview(ReviewSearchRequestDto dto){
//        // 1. Q클래스 정의 - Q도메인 클래스
////        QReview review = QReview.review;
//
//        // 2.
//
//
//
//    }

}

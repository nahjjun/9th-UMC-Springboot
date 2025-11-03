package com.example.umc_springboot.domain.review.mapper;

import com.example.umc_springboot.domain.review.dto.response.ReviewResponseDto;
import com.example.umc_springboot.domain.review.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    /**
     * Review를 ReviewResponseDto로 변경해주는 함수
     * @param review
     * @return ReviewResponseDto : 해당 리뷰와 관련된 데이터들이 담겨 있음.
     */
    public ReviewResponseDto toReviewResponseDto(Review review){
        if(review == null){
            return null;
        }
        String storeName = null;
        if(review.getStore() != null){
            storeName = review.getStore().getName();
        }

        String nickName = null;
        if(review.getUser() != null){
            nickName = review.getUser().getName();
        }

        List<String> photoUrls = new ArrayList<String>();
        if (review.getReviewPhotoList() != null){
            review.getReviewPhotoList().forEach(photo -> {
                photoUrls.add(photo.getUrl());
            });
        }

        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .storeName(storeName)
                .nickname(nickName)
                .star(review.getStar())
                .body(review.getBody())
                .photoUrls(photoUrls)
                .reviewDate(review.getCreatedAt().toLocalDate())
                .build();
    }


}

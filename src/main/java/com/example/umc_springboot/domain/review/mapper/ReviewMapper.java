package com.example.umc_springboot.domain.review.mapper;

import com.example.umc_springboot.domain.review.dto.request.ReviewCreateReqDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewListResDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResDto;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ReviewMapper {


    /*
    * ReviewCreateReqDto -> Review로 변환하는 함수
    *
    * */
    public Review toEntity(ReviewCreateReqDto dto, User user, Store store) {
        return Review.builder()
                .user(user)
                .store(store)
                .star(dto.star())
                .body(dto.body())
                .build();
    }



    /**
     * Review를 ReviewResponseDto로 변경해주는 함수
     * @param review : Review 엔티티
     * @param photoUrlMap : key=reviewId, value=해당 리뷰에 담긴 사진들의 url 리스트
     * @return ReviewResDto : 해당 리뷰와 관련된 데이터들이 담겨 있음.
     */
    public ReviewResDto toReviewResponseDto(Review review, Map<Long, List<String>> photoUrlMap){
        if(review == null){
            return null;
        }
        String storeName = review.getStore() != null ?  review.getStore().getName() : null;
        String nickName = review.getUser() != null ? review.getUser().getName() : null;

        // photoUrlMap에서 해당 리뷰의 pk에 맞는 사진 리스트가 있는지 찾고, 있으면 넣고 없으면 빈 리스트 넣어서 반환해준다.
        List<String> photoUrls = photoUrlMap.getOrDefault(review.getId(), Collections.emptyList());

        return ReviewResDto.builder()
                .reviewId(review.getId())
                .storeName(storeName)
                .nickname(nickName)
                .star(review.getStar())
                .body(review.getBody())
                .photoUrls(photoUrls)
                .reviewDate(review.getCreatedAt().toLocalDate())
                .build();
    }


    public ReviewListResDto toReviewListResponseDto(Page<ReviewResDto> pageResult){
        return ReviewListResDto.builder()
                .reviewList(pageResult.getContent())
                .listSize(pageResult.getNumberOfElements())
                .totalPage(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .isFirst(pageResult.isFirst())
                .isLast(pageResult.isLast())
                .build();
    }

}

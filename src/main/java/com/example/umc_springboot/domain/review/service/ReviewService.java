package com.example.umc_springboot.domain.review.service;

import com.example.umc_springboot.domain.review.dto.request.ReviewSearchRequestDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResponseDto;
import com.example.umc_springboot.domain.review.entity.QReview;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.domain.review.exception.ReviewErrorCode;
import com.example.umc_springboot.domain.review.mapper.ReviewMapper;
import com.example.umc_springboot.domain.review.repository.ReviewRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    /**
     * 들어온 검색 기준들을 기반으로 사용자가 작성한 리뷰를 검색해서 반환하는 함수
     * @param dto : 리뷰 데이터를 어떤 기준으로 검색해서 반환할 것인지가 들어있는 dto
     *            page - 몇번째 페이지를 반환하는지
     *            size - 한 페이지에 몇개의 데이터를 담을지
     *            dong - 어느 지역의 "동"의 리뷰를 반환할지 (null 가능)
     *            star - 별점이 몇점 이상의 리뷰들을 반환할 것인지 (default 0)
     *            storeId - 가게 아이디 기준으로 해당 가게의 리뷰들을 검색할 때 지정하는 값
     *            type - 해당 리뷰 검색 요청이 어떤 검색 요청인지
     * @return : dto에 들어있는 검색 기준으로 검색된 ReviewSearchResponseDto의 List
     */
    public List<ReviewResponseDto> searchReviews(ReviewSearchRequestDto dto){
        // 1. Q클래스 정의 - Q도메인 클래스
        // Q클래스는 엔티티를 QueryDSL이 이해할 수 있는 테이블 표현용 클래스로 변환한 것이다.
        // 프로젝트를 빌드하면, @Entity가 붙은 클래스들은 build/generated/... 폴더에 만들어진다.
        QReview review = QReview.review;

        // 2. BooleanBuilder 생성 - 여러 조건들을 AND/OR로 조합하고, 동적으로 추가하는 도구
        BooleanBuilder builder = new BooleanBuilder();

        // 3. Page 설정 - Pageable & PageRequest 클래스 사용
        // PageRequest : Pageable 인터페이스의 구현체. -> 페이지 번호, 페이지 크기, 정렬 정보를 담아 JPA/QueryDSL 레포지토리에 전달함
        Pageable pageable = (Pageable)PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        // Sort.by() : 정렬 정보를 만드는 static 메서드
        // Direction (ASC-오름차순, DESC-내림차순), property 지정 가능


        // 4. BooleanBuilder 설정하기
        // Review를 검색할 조건 구분하기
        switch (dto.getType()){
            // Dong 기준으로만 검색
            // 사용자가 입력한 Dong과 같은 동만 가져온다.
            case SearchRequestType.DONG:
                builder.and(review.store.address.dong.eq(dto.getDong()));
                    // ㄴ> BooleanBuilder에서 제공하는 함수들의 인자로 QClass의 BooleanExpression 메서드들을 지정해줌으로써, 제약조건들을 설정한다.
                break;
            // 별점 기준으로만 검색할 경우
            case SearchRequestType.STAR:
                builder.and(review.star.goe(dto.getStar()));
                break;
            // 주어진 STORE ID의 Store에 등록된 리뷰들을 가져오는 경우
            case SearchRequestType.STORE:
                // 1. dto의 storeId가 null인지 확인
                if(dto.getStoreId() == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_STORE_ID);
                }
                // 2. 해당 리뷰에 연결된 가게 id가 dto에 담겨있는 storeId와 같은지 조건
                builder.and(review.store.id.eq(dto.getStoreId()));
                break;
            // Dong, 별점 기준으로 검색할 경우
            case SearchRequestType.DONG_STAR:
                builder.and(review.store.address.dong.eq(dto.getDong()));
                builder.and(review.star.goe(dto.getStar()));
                break;
            // 주어진 STORE ID의 Store에 등록된 리뷰들을 가져오는 경우
            case SearchRequestType.STORE_STAR:
                // 1. dto의 storeId가 null인지 확인
                if(dto.getStoreId() == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_STORE_ID);
                }
                // 2. 해당 리뷰에 연결된 가게 id가 dto에 담겨있는 storeId와 같은지 조건
                builder.and(review.store.id.eq(dto.getStoreId()));
                // 3. star 조건 추가
                builder.and(review.star.goe(dto.getStar()));
            break;
        }

        // 5. Pageable 클래스, 위에서 만든 BooleanBuilder를 Predicate로 전달해서 Page 가져오기
        // BooleanBuilder는 Predicate를 구현한 구체 클래스임
        Page<Review> pageResult = reviewRepository.searchReview(builder, pageable);

        return pageResult.map(reviewMapper::toReviewResponseDto)
                .getContent(); // Page getContent() 함수로 하면 List를 반환한다.
    }

}

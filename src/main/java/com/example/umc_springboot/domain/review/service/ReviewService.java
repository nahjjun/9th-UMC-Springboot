package com.example.umc_springboot.domain.review.service;

import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.dto.request.ReviewCreateReqDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewListResDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResDto;
import com.example.umc_springboot.domain.review.entity.QReview;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.domain.review.exception.ReviewErrorCode;
import com.example.umc_springboot.domain.review.mapper.ReviewMapper;
import com.example.umc_springboot.domain.review.repository.ReviewRepository;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.exception.StoreErrorCode;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import com.example.umc_springboot.domain.user.entity.QUser;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createReview(ReviewCreateReqDto dto){
        // 1. user 가져오기
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 2. store 가져오기
        Store store = storeRepository.findById(dto.storeId()).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        // 3. user, store, dto로 review 생성
        Review review = reviewMapper.toEntity(dto, user, store);

        // 4. review Photo List에 reviewPhoto들 만들어서 추가
        List<String> reviewPhotoList = dto.reviewPhotoUrlList();
        reviewPhotoList.forEach(review::addReviewPhoto);

        reviewRepository.save(review);
    }


    /**
     * 들어온 검색 기준들을 기반으로 사용자가 작성한 리뷰를 검색해서 반환하는 함수
     * @param dong - 어느 지역의 "동"의 리뷰를 반환할지 (null 가능)
     * @param star - 별점이 몇점 이상의 리뷰들을 반환할 것인지 (default 0)
     * @param type - 해당 리뷰 검색 요청이 어떤 검색 요청인지
     * @return : dto에 들어있는 검색 기준으로 검색된 ReviewListResDto
     */
    @Transactional(readOnly = true)
    public ReviewListResDto searchReviews(Dong dong, Integer star, Long storeId, Long userId, SearchRequestType type, Pageable pageable) {
        // 1. Q클래스 정의 - Q도메인 클래스
        // Q클래스는 엔티티를 QueryDSL이 이해할 수 있는 테이블 표현용 클래스로 변환한 것이다.
        // 프로젝트를 빌드하면, @Entity가 붙은 클래스들은 build/generated/... 폴더에 만들어진다.
        QReview review = QReview.review;
        QUser user = QUser.user;

        // 2. BooleanBuilder 생성 - 여러 조건들을 AND/OR로 조합하고, 동적으로 추가하는 도구
        BooleanBuilder builder = new BooleanBuilder();

        // 3. BooleanBuilder 설정하기
        // Review를 검색할 조건 구분하기
        switch (type){
            // Dong 기준으로만 검색
            // 사용자가 입력한 Dong과 같은 동만 가져온다.
            case SearchRequestType.DONG:
                if (dong == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_DONG);
                }
                builder.and(review.store.address.dong.eq(dong));
                    // ㄴ> BooleanBuilder에서 제공하는 함수들의 인자로 QClass의 BooleanExpression 메서드들을 지정해줌으로써, 제약조건들을 설정한다.
                break;
            // 별점 기준으로만 검색할 경우
            case SearchRequestType.STAR:
                builder.and(review.star.goe(star));
                break;
            // 주어진 STORE ID의 Store에 등록된 리뷰들을 가져오는 경우
            case SearchRequestType.STORE:
                // 1. storeId가 null인지 확인
                if(storeId == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_STORE_ID);
                }
                // 2. 해당 리뷰에 연결된 가게 id가 storeId와 같은지 조건
                builder.and(review.store.id.eq(storeId));
                break;
            case SearchRequestType.USER:
                // 1. userId가 null인지 확인
                if(userId == null){
                    throw new CustomException(UserErrorCode.USER_ID_IS_NULL);
                }
                // 2. 해당 리뷰에 연결된 userId가 userId와 같은지 조건
                builder.and(review.user.id.eq(userId));
                break;
            // Dong, 별점 기준으로 검색할 경우
            case SearchRequestType.DONG_STAR:
                if (dong == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_DONG);
                }
                builder.and(review.store.address.dong.eq(dong));
                builder.and(review.star.goe(star));
                break;
            // 주어진 STORE ID의 Store에 등록된 리뷰들을 가져오는 경우
            case SearchRequestType.STORE_STAR:
                // 1. dto의 storeId가 null인지 확인
                if(storeId == null){
                    throw new CustomException(ReviewErrorCode.SEARCH_REVIEW_WITH_NULL_STORE_ID);
                }
                // 2. 해당 리뷰에 연결된 가게 id가 dto에 담겨있는 storeId와 같은지 조건
                builder.and(review.store.id.eq(storeId));
                // 3. star 조건 추가
                builder.and(review.star.goe(star));
                break;
        }

        // 5. Pageable 클래스, 위에서 만든 BooleanBuilder를 Predicate로 전달해서 Page 가져오기
        // BooleanBuilder는 Predicate를 구현한 구체 클래스임
        Page<ReviewResDto> pageResult = reviewRepository.searchReviews(builder, pageable);

        // 6. pageResult가 갖고 있는 메타 데이터를 ReviewListResDto에 담아서 반환
        return reviewMapper.toReviewListResponseDto(pageResult);
    }
}
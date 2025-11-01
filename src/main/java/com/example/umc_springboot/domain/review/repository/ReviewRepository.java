package com.example.umc_springboot.domain.review.repository;

import com.example.umc_springboot.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 사용자 아이디와 가게 아이디가 같은 리뷰가 존재하는지 확인
    // 사용자가 리뷰를 등록할 때, 해당 사용자가 그 가게에 리뷰를 등록했는지 여부를 확인해야하기 때문임
    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

//    // 지역으로 리뷰 조회하기
//    @Query(
//            value = "select r1.* " +
//                    "from review r1 " +
//                    "join store s1 on r1.store_id = s1.id " +
//                    "join address a1 on s1.address_id = a1.id " +
//                    "where a1.dong = :dong", nativeQuery = true
//    )
//    List<Review> searchReviewByAddressDong(@Param("dong") String dong);

}

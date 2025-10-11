package com.example.umc_springboot.Domain.Review.Repository;

import com.example.umc_springboot.Domain.Review.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 사용자 아이디와 가게 아이디가 같은 리뷰가 존재하는지 확인
    // 사용자가 리뷰를 등록할 때, 해당 사용자가 그 가게에 리뷰를 등록했는지 여부를 확인해야하기 때문임
    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

}

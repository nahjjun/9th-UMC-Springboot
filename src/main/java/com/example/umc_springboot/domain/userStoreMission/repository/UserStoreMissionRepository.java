package com.example.umc_springboot.domain.userStoreMission.repository;

import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import com.example.umc_springboot.domain.userStoreMission.enums.UserStoreMissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoreMissionRepository extends JpaRepository<UserStoreMission, Long> {

    boolean existsByUserId(Long userId);

    // 사용자 아이디와 미션의 상태에 맞는 데이터만 페이징으로 가져오는 함수
    Page<UserStoreMission> findByUserIdAndStatus(Long userId, UserStoreMissionStatus status, Pageable pageable);
}

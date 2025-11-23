package com.example.umc_springboot.domain.userMission.repository;

import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndMissionId(Long userId, Long missionId);
    boolean existsByUserIdAndStatus(Long userId, UserMissionStatus status);

    // 사용자 아이디와 미션의 상태에 맞는 데이터만 페이징으로 가져오는 함수
    Page<UserMission> findByUserIdAndStatus(Long userId, UserMissionStatus status, Pageable pageable);
}

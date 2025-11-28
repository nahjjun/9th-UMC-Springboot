package com.example.umc_springboot.domain.userMission.repository;

import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.domain.userMission.repository.querydsl.UserMissionQueryDsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// interface는 extends로 여러 부모 인터페이스를 다중 상속 받을 수 있음.
public interface UserMissionRepository extends JpaRepository<UserMission, Long>, UserMissionQueryDsl {

    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndMissionId(Long userId, Long missionId);
    boolean existsByUserIdAndStatus(Long userId, UserMissionStatus status);

    // 사용자 아이디와 미션의 상태에 맞는 데이터만 페이징으로 가져오는 함수
    Page<UserMission> findByUserIdAndStatus(Long userId, UserMissionStatus status, Pageable pageable);
}

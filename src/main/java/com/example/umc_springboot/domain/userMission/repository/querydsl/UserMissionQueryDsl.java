package com.example.umc_springboot.domain.userMission.repository.querydsl;

import com.example.umc_springboot.domain.mission.dto.response.UserMissionResDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// UserMission에서 사용할 QueryDsl 인터페이스
public interface UserMissionQueryDsl {
    /**
     * Dto Projection으로 Page<UserMissionResDto>를 반환하는 함수
     * @param predicate where 절 조건 객체. BooleanBuilder는 Predicate를 구현한 구현체이다.
     * @param pageable pageable 객체
     * @return UserMissionResDto
     */
    Page<UserMissionResDto> searchUserMissions(Predicate predicate, Pageable pageable);

}

package com.example.umc_springboot.domain.userMission.repository.querydsl;


import com.example.umc_springboot.domain.mission.dto.response.UserMissionResDto;
import com.example.umc_springboot.domain.mission.entity.QMission;
import com.example.umc_springboot.domain.store.entity.QStore;
import com.example.umc_springboot.domain.userMission.entity.QUserMission;
import com.example.umc_springboot.domain.userMission.repository.UserMissionRepository;
import com.example.umc_springboot.global.util.QueryDslUtil;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 커스텀 레포지토리임을 표현해주기 위해 붙임
@RequiredArgsConstructor
public class UserMissionQueryDslImpl implements UserMissionQueryDsl {
    private final JPAQueryFactory queryFactory;

    /**
     * Dto Projection으로 Page<UserMissionResDto>를 반환하는 함수
     * @param predicate where 절 조건 객체. BooleanBuilder는 Predicate를 구현한 구현체이다.
     * @param pageable pageable 객체
     * @return UserMissionResDto
     */
    @Override
    public Page<UserMissionResDto> searchUserMissions(Predicate predicate, Pageable pageable){
        // 1. Q클래스 선언
        QUserMission userMission = QUserMission.userMission;
        QMission mission = QMission.mission;
        QStore store = QStore.store;

        // 2. Dto Projection으로 데이터 페이징 조회
            // JPAQeuryFactory : JPA 환경에서 타입 안전한 쿼리를 쉽게 작성하도록 도와주는 클래스
        List<UserMissionResDto> content = queryFactory
                .select(Projections.constructor(UserMissionResDto.class,
                        userMission.id,
                        userMission.status,
                        mission.detail,
                        mission.point,
                        store.name
                ))
                .from(userMission)
                .join(userMission.mission, mission)
                .join(mission.store, store)
                .where(predicate)
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, mission))
                .fetch();

        // 3. 페이징을 위한 userMission에서 countQuery 조회
        JPAQuery<Long> countQuery = queryFactory
                .select(userMission.count())
                .from(userMission)
                .where(predicate);

        // 4. PageableExecutionUtils 클래스 .getPage(): 검색한 페이징 결과(content)를 감싸주는 메서드
        // 이미 페이징된 데이터(content)와 pageable 정보를 받아서, 필요한 경우 count 쿼리를 실행해 Page 객체를 만들어준다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


}

package com.example.umc_springboot.domain.mission.service;

import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.mission.dto.response.UserMissionResDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.mission.entity.QMission;
import com.example.umc_springboot.domain.mission.exception.MissionErrorCode;
import com.example.umc_springboot.domain.mission.mapper.MissionMapper;
import com.example.umc_springboot.domain.mission.repository.MissionRepository;
import com.example.umc_springboot.domain.review.mapper.ReviewMapper;
import com.example.umc_springboot.domain.store.entity.QStore;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.exception.StoreErrorCode;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import com.example.umc_springboot.domain.mission.enums.MissionStatus;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.domain.userMission.entity.QUserMission;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.domain.userMission.exception.UserMissionErrorCode;
import com.example.umc_springboot.domain.userMission.repository.UserMissionRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.example.umc_springboot.global.response.PageResponse;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MissionMapper missionMapper;
    private final ReviewMapper reviewMapper;

    /**
     * storeId, missionId를 담은 dto를 입력받아, 가게에 등록할 미션을 새로 생성하는 함수
     */
    @Transactional
    public void createMission(CreateMissionReqDto dto, Long storeId) {
        // store가 실제로 있는지 확인
        if(!storeRepository.existsById(storeId)){
            throw new CustomException(StoreErrorCode.STORE_NOT_FOUND);
        }

        // 1. Store 가져오기
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        // 2. Mission 생성
        Mission mission = missionMapper.toEntity(dto, store);

        // 3. mission 저장
        missionRepository.save(mission);
    }


    /**
     * 가게가 등록함 미션을 조회하는 함수
     * @param storeId
     * @param missionType
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    // 데이터가 Mission 엔티티에 한정되어있으므로 JPA로만 처리
    public PageResponse<MissionResDto> searchMissions(Long storeId, MissionStatus missionType, Pageable pageable) {
        // 1. 해당 가게가 존재하는지 확인
        if(!storeRepository.existsById(storeId)){
            throw new CustomException(StoreErrorCode.STORE_NOT_FOUND);
        }

        // 2. Page 객체 받기
        Page<Mission> pageData = missionRepository.findByStoreIdAndStatus(storeId, missionType, pageable);

        // 3. PageResponse로 변환
        return PageResponse.of(pageData, missionMapper::toMissionResDto);
    }



    /*
    *   userId, missionId를 담은 dto를 입력받아 새로운 userMission을 생성해주는 함수
    * */
    @Transactional
    public void challengeMission(ChallengeMissionReqDto dto, Long userId) {
        // 1. user 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 2. Mission 찾기
        Mission mission = missionRepository.findById(dto.missionId()).orElseThrow(() -> new CustomException(MissionErrorCode.MISSION_NOT_FOUND));

        // 3. Mission이 진행 가능한 상태인지(해당 가게가 올린 미션이 활성화 상태인지) 확인
        if (mission.getStatus() == MissionStatus.INACTIVATE) {
            throw new CustomException(MissionErrorCode.MISSION_INACTIVE);
        }

        // 4. 이미 해당 미션에 도전한 상태인지 확인
        if(userMissionRepository.existsByUserIdAndMissionId(user.getId(), mission.getId())){
            throw new CustomException(UserMissionErrorCode.USER_ALREADY_CHALLENGED);
        }

        // 5. UserMission 객체 생성
        UserMission userMission = missionMapper.toUserMission(user, mission);

        // 6. UserMission 저장
        userMissionRepository.save(userMission);
    }

    /**
     * 사용자가 할당받은 미션들의 목록을 조회하는 함수
     * @param userId 사용자가 신청한 미션을 조회하기 위한 특정 사용자의 ID
     * @param userMissionStatus 사용자가 신청한 미션
     * @return PageResponse<UserMissionResDto>
     */
    // 연결된 데이터가 있으므로 querydsl로 내부 로직 최적화
    @Transactional(readOnly = true)
    public PageResponse<UserMissionResDto> searchUserMissions(Long userId, UserMissionStatus userMissionStatus, Pageable pageable) {
        // 1. 해당 사용자가 존재하지 않는지 확인
        if(!userRepository.existsById(userId)){
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }

        // switch문으로 매번 db에 접근해서 쿼리를 가져오면 성능 저하가 일어난다! 따라서 userMissionStatus 값대로 분리해서 에러처리하는 코드 삭제

        // 2. QClass, BooleanBuilder로 조건문 생성하기
        QUserMission userMission = QUserMission.userMission;
        QMission mission = QMission.mission;
        QStore store = QStore.store;

        BooleanBuilder builder = new BooleanBuilder();

        // 사용자 id와 미션 status가 같은 데이터만 가져오기
        builder.and(userMission.user.id.eq(userId));
        builder.and(userMission.status.eq(userMissionStatus));

        // 2. 페이징으로 해당 데이터 가져오기
        Page<UserMissionResDto> pageData = userMissionRepository.searchUserMissions(builder, pageable);

        // 3. PageResponse로 변환
        return PageResponse.of(pageData);
    }

}

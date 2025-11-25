package com.example.umc_springboot.domain.mission.service;

import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.mission.exception.MissionErrorCode;
import com.example.umc_springboot.domain.mission.mapper.MissionMapper;
import com.example.umc_springboot.domain.mission.repository.MissionRepository;
import com.example.umc_springboot.domain.review.mapper.ReviewMapper;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.exception.StoreErrorCode;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import com.example.umc_springboot.domain.mission.enums.MissionStatus;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.domain.userMission.exception.UserMissionErrorCode;
import com.example.umc_springboot.domain.userMission.repository.UserMissionRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.example.umc_springboot.global.response.PageResponse;
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
        // 1. Store 가져오기
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        // 2. Mission 생성
        Mission mission = missionMapper.toEntity(dto, store);

        // 3. mission 저장
        missionRepository.save(mission);
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
     * @param userId
     * @param userMissionStatus
     * @return PageResponse<MissionResDto>
     */
    // querydsl로 리팩토링하기!!
    @Transactional(readOnly = true)
    public PageResponse<MissionResDto> searchUserMissions(Long userId, UserMissionStatus userMissionStatus, Pageable pageable) {
        // 1. 해당 사용자가 존재하지 않는지 확인
        if(!userRepository.existsById(userId)){
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }

        // 2. userMissionStatus따라 구분
        switch (userMissionStatus) {
            case UserMissionStatus.IN_PROGRESS:
                // 1. 도전중인 미션 없는 경우 확인
                if(!userMissionRepository.existsByUserIdAndStatus(userId, userMissionStatus)){
                    throw new CustomException(UserMissionErrorCode.USER_CHALLENGING_MISSION_EMPTY);
                }
                break;
            case UserMissionStatus.COMPLETED:
                // 1. 완료된 미션 없는 경우 확인
                if(!userMissionRepository.existsByUserIdAndStatus(userId, userMissionStatus)){
                    throw new CustomException(UserMissionErrorCode.USER_COMPLETED_MISSION_EMPTY);
                }
                break;
            case UserMissionStatus.EXPIRED:
                // 1. 기간 만료된 미션 없는 경우 확인
                if(!userMissionRepository.existsByUserIdAndStatus(userId, userMissionStatus)){
                    throw new CustomException(UserMissionErrorCode.USER_EXPIRED_MISSION_EMPTY);
                }
                break;
            case UserMissionStatus.FAILED:
                // 1. 실패한 미션 없는 경우 확인
                if(!userMissionRepository.existsByUserIdAndStatus(userId, userMissionStatus)){
                    throw new CustomException(UserMissionErrorCode.USER_FAILED_MISSION_EMPTY);
                }
                break;
        }

        // 2. 페이징으로 해당 데이터 가져오기
        Page<UserMission> pageData =  userMissionRepository.findByUserIdAndStatus(userId, userMissionStatus, pageable);

        // 3. 페이징된 데이터로 entity -> mapper -> dto 변경한 뒤 PageResponse<MissionResDto>로 변경
        return PageResponse.of(pageData, missionMapper::toMissionResDto);
    }

}

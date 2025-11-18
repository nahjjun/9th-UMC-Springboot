package com.example.umc_springboot.domain.mission.service;

import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.mission.exception.MissionErrorCode;
import com.example.umc_springboot.domain.mission.mapper.MissionMapper;
import com.example.umc_springboot.domain.mission.repository.MissionRepository;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.exception.StoreErrorCode;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import com.example.umc_springboot.domain.mission.enums.MissionStatus;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.exception.UserMissionErrorCode;
import com.example.umc_springboot.domain.userMission.repository.UserMissionRepository;
import com.example.umc_springboot.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
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

}

package com.example.umc_springboot.domain.mission.service;

import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionRequestDto;
import com.example.umc_springboot.domain.mission.mapper.MissionMapper;
import com.example.umc_springboot.domain.storeMission.entity.StoreMission;
import com.example.umc_springboot.domain.storeMission.enums.StoreMissionStatus;
import com.example.umc_springboot.domain.storeMission.exception.StoreMissionErrorCode;
import com.example.umc_springboot.domain.storeMission.repository.StoreMissionRepository;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import com.example.umc_springboot.domain.userStoreMission.exception.UserStoreMissionErrorCode;
import com.example.umc_springboot.domain.userStoreMission.repository.UserStoreMissionRepository;
import com.example.umc_springboot.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final UserRepository userRepository;
    private final StoreMissionRepository storeMissionRepository;
    private final UserStoreMissionRepository userStoreMissionRepository;
    private final MissionMapper missionMapper;

    /*
    *   userId, storeMissionId를 담은 dto를 입력받아 새로운 userStoreMission을 생성해주는 함수
    * */
    @Transactional
    public void challengeMission(ChallengeMissionRequestDto dto) {
        // 1. user 찾기
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 2. storeMission 찾기
        StoreMission storeMission = storeMissionRepository.findById(dto.storeMissionId()).orElseThrow(() -> new CustomException(StoreMissionErrorCode.STORE_MISSION_NOT_FOUND));

        // 3. StoreMission이 진행 가능한 상태인지(해당 가게가 올린 미션이 활성화 상태인지) 확인
        if (storeMission.getStatus() == StoreMissionStatus.INACTIVATE) {
            throw new CustomException(StoreMissionErrorCode.STORE_MISSION_INACTIVE);
        }

        // 4. 이미 해당 미션에 도전한 상태인지 확인
        if(userStoreMissionRepository.existsByUserIdAndStoreMissionId(user.getId(), storeMission.getId())){
            throw new CustomException(UserStoreMissionErrorCode.USER_ALREADY_CHALLENGED);
        }


        // 5. UserStoreMission 객체 생성
        UserStoreMission userStoreMission = missionMapper.toUserStoreMission(user, storeMission);

        // 6. UserStoreMission 저장
        userStoreMissionRepository.save(userStoreMission);
    }

}

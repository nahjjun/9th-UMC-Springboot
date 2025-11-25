package com.example.umc_springboot.domain.mission.mapper;


import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MissionMapper {

    public Mission toEntity(CreateMissionReqDto dto, Store store){
        return Mission.builder()
                .detail(dto.detail())
                .point(dto.point())
                .store(store)
                .build();
    }


    public UserMission toUserMission(User user, Mission mission) {
        return UserMission.builder()
                .user(user)
                .mission(mission)
                .build();
    }

    // Query Dsl로 리팩토링해야함
    public MissionResDto toMissionResDto(UserMission userMission) {
        return MissionResDto.builder()
                .userMissionId(userMission.getId())
                .userMissionStatus(userMission.getStatus())
                .missionPoint(userMission.getMission().getPoint())
                .storeName(userMission.getMission().getStore().getName())
                .build();
    }

}

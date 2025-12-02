package com.example.umc_springboot.domain.mission.mapper;


import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.mission.dto.response.UserMissionResDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public MissionResDto toMissionResDto(Mission mission){
        return MissionResDto.builder()
                .missionId(mission.getId())
                .detail(mission.getDetail())
                .point(mission.getPoint())
                .missionStatus(mission.getStatus())
                .build();
    }

    // Query Dsl로 리팩토링해야함
    public UserMissionResDto toUserMissionResDto(UserMission userMission) {
        return UserMissionResDto.builder()
                .userMissionId(userMission.getId())
                .detail(userMission.getMission().getDetail())
                .userMissionStatus(userMission.getStatus())
                .missionPoint(userMission.getMission().getPoint())
                .storeName(userMission.getMission().getStore().getName())
                .build();
    }

}

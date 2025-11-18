package com.example.umc_springboot.domain.mission.mapper;


import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
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
                .store(store)
                .build();
    }


    public UserMission toUserMission(User user, Mission mission) {
        return UserMission.builder()
                .user(user)
                .mission(mission)
                .build();
    }
}

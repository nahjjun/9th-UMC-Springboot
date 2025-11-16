package com.example.umc_springboot.domain.mission.mapper;


import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionRequestDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.storeMission.entity.StoreMission;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionMapper {

    public UserStoreMission toUserStoreMission(User user, StoreMission storeMission) {
        return UserStoreMission.builder()
                .user(user)
                .storeMission(storeMission)
                .build();
    }
}

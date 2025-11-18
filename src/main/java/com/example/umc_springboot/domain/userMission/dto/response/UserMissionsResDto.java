package com.example.umc_springboot.domain.userMission.dto.response;

import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserMissionsResDto {
    private final Long userMissionId;
    private final UserMissionStatus userMissionStatus;
    private final Integer missionPoint;
    private final String storeName;

    public static UserMissionsResDto from(UserMission userMission) {
        return UserMissionsResDto.builder()
                .userMissionId(userMission.getId())
                .userMissionStatus(userMission.getStatus())

                .missionPoint(userMission.getMission().getPoint())
                .storeName(userMission.getMission().getStore().getName())
                .build();
    }
}

package com.example.umc_springboot.Domain.UserStoreMission.Dto.Response;

import com.example.umc_springboot.Domain.Mission.Enums.MissionModifier;
import com.example.umc_springboot.Domain.Mission.Enums.MissionType;
import com.example.umc_springboot.Domain.UserStoreMission.Entity.UserStoreMission;
import com.example.umc_springboot.Domain.UserStoreMission.Enums.UserStoreMissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MissionsResponseDto {
    private final Long userStoreMissionId;
    private final UserStoreMissionStatus userStoreMissionStatus;
    private final Integer missionPriceCriteria;
    private final MissionModifier missionModifier;
    private final MissionType missionType;
    private final Integer missionPoint;
    private final String storeName;

    public static MissionsResponseDto from(UserStoreMission userStoreMission) {
        return MissionsResponseDto.builder()
                .userStoreMissionId(userStoreMission.getId())
                .userStoreMissionStatus(userStoreMission.getStatus())
                .missionPriceCriteria(userStoreMission.getStoreMission().getMission().getPriceCriteria())
                .missionModifier(userStoreMission.getStoreMission().getMission().getModifier())
                .missionType(userStoreMission.getStoreMission().getMission().getType())
                .missionPoint(userStoreMission.getStoreMission().getMission().getPoint())
                .storeName(userStoreMission.getStoreMission().getStore().getName())
                .build();
    }
}

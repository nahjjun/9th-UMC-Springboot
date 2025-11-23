package com.example.umc_springboot.domain.mission.dto.response;

import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import lombok.Builder;

@Builder
public record MissionResDto(
        Long userMissionId,
        UserMissionStatus userMissionStatus,
        Integer missionPoint,
        String storeName
) {
}

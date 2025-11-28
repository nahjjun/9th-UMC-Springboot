package com.example.umc_springboot.domain.mission.dto.response;

import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import lombok.Builder;

@Builder
public record UserMissionResDto(
        Long userMissionId,
        UserMissionStatus userMissionStatus,
        String detail,
        Integer missionPoint,
        String storeName
) {
}

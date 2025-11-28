package com.example.umc_springboot.domain.mission.dto.response;

import com.example.umc_springboot.domain.mission.enums.MissionStatus;
import lombok.Builder;

@Builder
public record MissionResDto(
        Long missionId,
        String detail,
        Integer point,
        MissionStatus missionStatus
) {
}

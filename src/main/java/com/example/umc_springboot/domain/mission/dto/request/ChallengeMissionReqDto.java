package com.example.umc_springboot.domain.mission.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChallengeMissionReqDto(
        @NotNull(message = "missionId가 null입니다")
        Long missionId
) {
}

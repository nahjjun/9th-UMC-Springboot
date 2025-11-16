package com.example.umc_springboot.domain.mission.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChallengeMissionRequestDto(
        @NotNull(message = "userId가 null입니다")
        Long userId,
        @NotNull(message = "storeMissionId가 null입니다")
        Long storeMissionId
) {
}

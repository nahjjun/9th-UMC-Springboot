package com.example.umc_springboot.Domain.UserStoreMission.Enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum UserStoreMissionStatus {
    @Schema(description = "미션 진행 중")
    IN_PROGRESS,
    @Schema(description = "미션 완료")
    COMPLETED,
    @Schema(description = "미션 실패")
    FAILED,
    @Schema(description = "미션 기간 만료됨")
    EXPIRED
}

package com.example.umc_springboot.domain.mission.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMissionReqDto(
        @NotBlank(message = "미션 내용을 입력해야합니다.")
        String detail
) {
}

package com.example.umc_springboot.Domain.UserStoreMission.Dto.Request;

import com.example.umc_springboot.Domain.UserStoreMission.Enums.UserStoreMissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class MissionsRequestDto {
    private final Long userId;
    private final UserStoreMissionStatus status;
    private final Integer page;

}

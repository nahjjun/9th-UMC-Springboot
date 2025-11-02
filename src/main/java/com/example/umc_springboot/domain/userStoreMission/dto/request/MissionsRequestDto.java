package com.example.umc_springboot.domain.userStoreMission.dto.request;

import com.example.umc_springboot.domain.userStoreMission.enums.UserStoreMissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class MissionsRequestDto {
    private final Long userId;
    private final UserStoreMissionStatus status;
    private final Integer page;

}

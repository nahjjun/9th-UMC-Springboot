package com.example.umc_springboot.domain.userMission.dto.request;

import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMissionsReqDto {
    private final Long userId;
    private final UserMissionStatus status;
    private final Integer page;

}

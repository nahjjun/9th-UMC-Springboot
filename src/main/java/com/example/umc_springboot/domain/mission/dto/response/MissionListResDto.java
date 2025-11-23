package com.example.umc_springboot.domain.mission.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MissionListResDto(
        List<MissionResDto> missionList,
        Integer listSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
) {
}

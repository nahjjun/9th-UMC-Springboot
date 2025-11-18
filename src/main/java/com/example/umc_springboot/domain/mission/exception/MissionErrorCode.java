package com.example.umc_springboot.domain.mission.exception;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.example.umc_springboot.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {
    MISSION_NOT_FOUND("M_001", "미션을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    MISSION_INACTIVE("SM_002", "가게가 등록한 미션이 비활성화 상태입니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

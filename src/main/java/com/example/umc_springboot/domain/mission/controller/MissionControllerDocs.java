package com.example.umc_springboot.domain.mission.controller;

import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.global.annotation.PageZero;
import com.example.umc_springboot.global.response.GlobalResponse;
import com.example.umc_springboot.global.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("")
@Tag(name = "Mission", description = "Mission 관련 API")
public interface MissionControllerDocs {
    @Operation(
            summary = "가게가 본인 가게의 미션을 생성하는 API",
            description = """
                    가게가 본인 가게에 등록할 미션을 새로 생성하는 API입니다.
                    
                    [JWT 인증 필요함]
                    
                    [Request Body]
                    - storeId: 가게 고유 ID (Long)
                    - point : 미션 성공 시 포인트 (Integer)
                    - detail : 미션 내용 (string)
                    
                    """

    )
    @PostMapping("/stores/{storeId}/missions")
    public ResponseEntity<GlobalResponse<?>> createMission(@RequestBody @Valid CreateMissionReqDto dto, @PathVariable Long storeId);


    @Operation(
            summary = "사용자가 가게가 올려둔 미션을 도전 신청하는 API",
            description = """
                    user는 가게가 등록해둔 미션을 도전 신청합니다.
                    
                    [인증 필요함]
                    
                    [Request Body]
                    - userId: Long
                    - storeId: Long
                    - missionId: Long
                    
                    """
    )
    @PostMapping("/users/{userId}/missions")
    public ResponseEntity<GlobalResponse<?>> challengeMission(@RequestBody @Valid ChallengeMissionReqDto dto, @PathVariable Long userId);


    @Operation(
            summary = "사용자가 신청한 미션들의 목록을 가져오는 API",
            description = """
                    사용자가 신청한 미션들의 목록을 가져오는 API
                    
                    [JWT 토큰 필요함]
                    """
    )
    @Parameters({
            @Parameter(name="page", description = "페이지번호(1부터 시작)", example = "1"),
            @Parameter(name = "size", description = "페이지 크기", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준(ex: createdAt,desc", example = "createdAt,desc")
    })
    @GetMapping("/users/{userId}/missions")
    public ResponseEntity<GlobalResponse<PageResponse<MissionResDto>>> searchUserMissions(
            @PathVariable @NotNull Long userId,
            @RequestParam(defaultValue = "IN_PROGRESS") @NotNull UserMissionStatus missionType,
            @PageZero Integer page,
            Integer size,
            String sort
            );

}

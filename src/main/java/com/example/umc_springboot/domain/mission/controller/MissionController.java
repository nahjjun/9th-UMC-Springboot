package com.example.umc_springboot.domain.mission.controller;


import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.service.MissionService;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "Mission", description = "Mission 관련 API")
public class MissionController {
    private final MissionService missionService;


    @Operation(
            summary = "가게가 본인 가게의 미션을 생성하는 API",
            description = """
                    가게가 본인 가게에 등록할 미션을 새로 생성하는 API입니다.
                    
                    [JWT 인증 필요함]
                    
                    [Request Body]
                    - storeId: 가게 고유 ID (Long)
                    - missionId: 미션 고유 ID (Long)
                    
                    """

    )
    @PostMapping("/stores/{storeId}/missions")
    public ResponseEntity<GlobalResponse<?>> createMission(@RequestBody @Valid CreateMissionReqDto dto, @PathVariable Long storeId) {
        missionService.createMission(dto, storeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("200", "가게 미션 등록 성공!"));
    }





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
    public ResponseEntity<GlobalResponse<?>> challengeMission(@RequestBody @Valid ChallengeMissionReqDto dto, @PathVariable Long userId) {
        missionService.challengeMission(dto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("미션 도전 신청 성공!"));
    }

}

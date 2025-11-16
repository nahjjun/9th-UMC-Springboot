package com.example.umc_springboot.domain.mission.controller;


import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionRequestDto;
import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.mission.service.MissionService;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "Mission", description = "Mission 관련 API")
public class MissionController {
    private final MissionService missionService;

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
    @PostMapping("/users/{userId}/store-missions")
    public ResponseEntity<GlobalResponse<?>> challengeMission(ChallengeMissionRequestDto dto) {
        missionService.challengeMission(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("미션 도전 신청 성공!"));
    }

}

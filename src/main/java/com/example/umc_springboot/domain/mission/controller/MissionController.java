package com.example.umc_springboot.domain.mission.controller;


import com.example.umc_springboot.domain.mission.dto.request.ChallengeMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.request.CreateMissionReqDto;
import com.example.umc_springboot.domain.mission.dto.response.MissionResDto;
import com.example.umc_springboot.domain.mission.service.MissionService;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.global.annotation.PageZero;
import com.example.umc_springboot.global.annotation.ValidSort;
import com.example.umc_springboot.global.response.GlobalResponse;
import com.example.umc_springboot.global.response.PageResponse;
import com.example.umc_springboot.global.util.PageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class MissionController implements MissionControllerDocs{
    private final MissionService missionService;
    private final PageUtil pageUtil;

    @Override
    public ResponseEntity<GlobalResponse<?>> createMission(@RequestBody @Valid CreateMissionReqDto dto, @PathVariable Long storeId) {
        missionService.createMission(dto, storeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("200", "가게 미션 등록 성공!"));
    }

    @Override
    public ResponseEntity<GlobalResponse<?>> challengeMission(@RequestBody @Valid ChallengeMissionReqDto dto, @PathVariable Long userId) {
        missionService.challengeMission(dto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("미션 도전 신청 성공!"));
    }

    @Override
    public ResponseEntity<GlobalResponse<PageResponse<MissionResDto>>> searchUserMissions(
            @PathVariable @NotNull Long userId,
            @RequestParam(defaultValue = "IN_PROGRESS") @NotNull UserMissionStatus missionType,
            @PageZero Integer page,
            Integer size,
            @ValidSort(target = UserMission.class) String sort){
        Pageable pageable = PageRequest.of(page - 1, size, pageUtil.parseSort(sort));
        PageResponse<MissionResDto> response = missionService.searchUserMissions(userId, missionType, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse.success("사용자가 할당받은 미션 목록 조회 성공!", response));
    }

}

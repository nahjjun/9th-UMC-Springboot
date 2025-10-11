package com.example.umc_springboot.Domain.UserStoreMission.Service;


import com.example.umc_springboot.Domain.UserStoreMission.Dto.Request.MissionsRequestDto;
import com.example.umc_springboot.Domain.UserStoreMission.Dto.Response.MissionsResponseDto;
import com.example.umc_springboot.Domain.UserStoreMission.Entity.UserStoreMission;
import com.example.umc_springboot.Domain.UserStoreMission.Enums.UserStoreMissionStatus;
import com.example.umc_springboot.Domain.UserStoreMission.Repository.UserStoreMissionRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserStoreMissionService {
    private final UserStoreMissionRepository userStoreMissionRepository;

    public List<MissionsResponseDto> getMissions(MissionsRequestDto missionsRequestDto) {
        Long userId = missionsRequestDto.getUserId();
        UserStoreMissionStatus status = missionsRequestDto.getStatus();
        Integer page = missionsRequestDto.getPage();

        // 해당 사용자에게 할당된 미션들이 있는지 확인
        if(!userStoreMissionRepository.existsById(userId)){
            throw new IllegalStateException("사용자에게 할당된 미션이 없습니다.");
        }

        // 페이징을 위한 Pageable 객체 선언
        Pageable pageable = (Pageable) PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // UserStoreMission 페이지 조회
        Page<UserStoreMission> pageResult = userStoreMissionRepository.findByUserIdAndStatus(userId, status, pageable);
            // ㄴ> Page.getContent(): List<UserStoreMission> 반환함

        return pageResult.map(MissionsResponseDto::from)
                .getContent();
    }

}

package com.example.umc_springboot.domain.userStoreMission.service;


import com.example.umc_springboot.domain.userStoreMission.dto.request.MissionsRequestDto;
import com.example.umc_springboot.domain.userStoreMission.dto.response.MissionsResponseDto;
import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import com.example.umc_springboot.domain.userStoreMission.enums.UserStoreMissionStatus;
import com.example.umc_springboot.domain.userStoreMission.repository.UserStoreMissionRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserStoreMissionService {
    private final UserStoreMissionRepository userStoreMissionRepository;

    @Transactional
    public List<MissionsResponseDto> getMissions(MissionsRequestDto missionsRequestDto) {
        Long userId = missionsRequestDto.getUserId();
        UserStoreMissionStatus status = missionsRequestDto.getStatus();
        Integer page = missionsRequestDto.getPage();

        // 굳이 existByUserId()로 존재 여부를 확인하지 않고, 그냥 findBy...로 가져와서 빈 리스트를 반환하는 것이 더욱 Restful한 설계일 것이다.
        // 페이징을 위한 Pageable 객체 선언
        Pageable pageable = (Pageable) PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // UserStoreMission 페이지 조회
        Page<UserStoreMission> pageResult = userStoreMissionRepository.findByUserIdAndStatus(userId, status, pageable);
            // ㄴ> Page.getContent(): List<UserStoreMission> 반환함

        return pageResult.map(MissionsResponseDto::from)
                .getContent();
    }

}

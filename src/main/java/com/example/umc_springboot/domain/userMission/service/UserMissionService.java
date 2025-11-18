package com.example.umc_springboot.domain.userMission.service;


import com.example.umc_springboot.domain.userMission.dto.request.UserMissionsReqDto;
import com.example.umc_springboot.domain.userMission.dto.response.UserMissionsResDto;
import com.example.umc_springboot.domain.userMission.entity.UserMission;
import com.example.umc_springboot.domain.userMission.enums.UserMissionStatus;
import com.example.umc_springboot.domain.userMission.repository.UserMissionRepository;
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
public class UserMissionService {
    private final UserMissionRepository userMissionRepository;

    @Transactional
    public List<UserMissionsResDto> getMissions(UserMissionsReqDto userMissionsReqDto) {
        Long userId = userMissionsReqDto.getUserId();
        UserMissionStatus status = userMissionsReqDto.getStatus();
        Integer page = userMissionsReqDto.getPage();

        // 굳이 existByUserId()로 존재 여부를 확인하지 않고, 그냥 findBy...로 가져와서 빈 리스트를 반환하는 것이 더욱 Restful한 설계일 것이다.
        // 페이징을 위한 Pageable 객체 선언
        Pageable pageable = (Pageable)PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // UserMission 페이지 조회
        Page<UserMission> pageResult = userMissionRepository.findByUserIdAndStatus(userId, status, pageable);
            // ㄴ> Page.getContent(): List<UserMission> 반환함

        return pageResult.map(UserMissionsResDto::from)
                .getContent();
    }

}

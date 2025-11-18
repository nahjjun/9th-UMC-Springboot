package com.example.umc_springboot.domain.store.controller;

import com.example.umc_springboot.domain.store.dto.request.CreateStoreReqDto;
import com.example.umc_springboot.domain.store.service.StoreService;
import com.example.umc_springboot.domain.user.service.UserService;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Tag(name = "Store 관련 API")
public class StoreController {
    private final StoreService storeService;

    @Operation(
            summary = "가게 신규 등록 API",
            description = """
                    가게를 신규 등록하는 API입니다. 가게 사장님이 생성합니다.
                    
                    [JWT 인증 필요]
                    
                    [Request Body]
                    - bossUniqueNum: 가게를 등록할 사장의 고유 식별 번호입니다. 6자리입니다. 
                    - name: 가게 이름
                    - type: 가게 종류 ( "KOREAN" | "JAPANESE" | "CHINESE" | "WESTERN" | "ASIAN" | "VIETNAMESE")
                    - photoUrl: 가게 대표 사진 url
                    - city: 시
                    - district: 구
                    - dong: 동
                    - detail: 상세 주소
                    """
    )
    @PostMapping("")
    public ResponseEntity<GlobalResponse<?>> createStore(@RequestBody @Valid CreateStoreReqDto dto) {
        storeService.createStore(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("가게 정보 등록 성공!"));
    }




}

package com.example.umc_springboot.domain.user.controller;


import com.example.umc_springboot.domain.user.dto.request.JoinRequestDto;
import com.example.umc_springboot.domain.user.dto.response.UserInfoResponseDto;
import com.example.umc_springboot.domain.user.service.UserService;
import com.example.umc_springboot.global.response.GlobalResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "회원가입",
            description = """
                    사용자 정보를 입력받아 회원가입을 수행합니다.
                    
                    """
    )
    @PostMapping("")
    public ResponseEntity<GlobalResponse<UserInfoResponseDto>> join(
            @Parameter(
                    name="joinRequestDto",
                    description = "회원가입 때 필요한 사용자 정보"
            )
            @Valid @RequestBody JoinRequestDto joinRequestDto)
    {
        userService.join(joinRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("회원가입 성공!"));
    }

    @Operation(
            summary = "사용자 정보 가져오기",
            description = """
                로그인된 사용자의 정보를 조회합니다.
                
                [인증 필요함]
                - 해당 API는 JWT 인증이 필요한 API입니다.
                - Request Header에 "Authorization: Bearer <AccessToken>"을 포함해아합니다.
                
                [Request Query]
                - field: 기본으로 제공하는 기본 응답 데이터 이외에 추가적인 User 데이터가 필요한 경우, 필드명들을 적어서 추가로 데이터들을 받아올 수 있습니다.
                - 지정 가능 필드들:
                    - id: 사용자 고유 Id
                    - name: 사용자 이름
                    - phoneNumber: 전화번호
                    - email: 이메일
                    - point: 사용자 보유 포인트
                    - gender: 성별
                    - birth: 생년월일
                    - address: 사용자 주소
                - 기본으로 응답되는 데이터들을 field에 적어도 중첩되어 데이터가 넘어가지는 않습니다.
                - 기본으로 응답되는 데이터들은 field 쿼리로 넘겨주지 않아도 됩니다!                
            """
    )
    @GetMapping("/{userId}")
    public ResponseEntity<GlobalResponse<?>> getUserInfo(
            @Parameter(
                    description = "사용자 고유 ID",
                    required = true
            )
            @PathVariable Long userId,
            @Parameter(
                    name = "field",
                    description = "응답에 추가로 받고 싶은 필드명 지정", // 변수 설명
                    example = "field=id,name,phoneNumber", // 데이터 예시
                    required = false // 해당 데이터가 필수인지 지정하는 속성
            )
            @RequestParam(required = false) String field
    ) {
        // 1. 기본 필드(모든 데이터가 담긴 응답 dto)
        UserInfoResponseDto responseDto = userService.getUserInfo(userId);

        // 2. 직렬화 래퍼(MappingJacksonValue) 생성
        // MappingJacksonValue : json 직렬화를 처리하는데 사용되는 클래스
        MappingJacksonValue mappingValue = new MappingJacksonValue(responseDto);

        // 3. 사용자로부터 온 field에 있는 화이트리스트 필드들 set으로 추출
        if(field != null && !field.isEmpty()){
            Set<String> fieldSet = Arrays
                    .stream(field.split(","))
                    .map(String::trim) // 공백 제거
                    .collect(Collectors.toSet()); // 위에서 나온 값들을 Collector로 Set으로 모으겠다는 의미


            // 4. 화이트리스트 필드들을 담은 필터를 생성한다.
            // .filterOutAllExcept(set): 주어진 Set에 있는 값들을 제외하고는 Dto의 다른 속성값들을 제외해주는 필터를 생성해주는 함수
            // .serializeAllExcept(set): 블랙리스트 기반 필터
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fieldSet);

            // 5. FilterProvider 설정
            // FilterProvider : 직렬화 시 어떤 필드를 포함/제외할지 정의하는 필터들을 관리하는 역할을 함
            FilterProvider filters = new SimpleFilterProvider()
                    .addFilter("fieldFilter", filter);
                // SimpleFilterProvider는 FilterProvider의 구현체로, 여러개의 필터를 필터 이름과 실제 필터 객체를 매핑해서 관리한다.
                // ㄴ> 한번에 여러 DTO 클래스에 다른 필터를 적용할 수 있도록, 필터 이름 -> 필터 규칙 맵핑 전체 세트를 넘겨줘야한다.

            // 6. 만들어두었던 직렬화 래퍼(MappingJacksonValue)에 필터 적용
            mappingValue.setFilters(filters);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse.success("사용자 정보 조회 성공!", mappingValue));
    }


}

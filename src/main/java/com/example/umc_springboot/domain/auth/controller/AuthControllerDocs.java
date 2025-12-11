package com.example.umc_springboot.domain.auth.controller;

import com.example.umc_springboot.domain.auth.dto.LoginReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthControllerDocs {
    /**
     * 실제 로그인은 Security Filter Chain에서 처리되므로,
     * 이 컨트롤러 메서드는 실행되지 않음 (Swagger 문서용).
     */
    @Operation(
            summary = "세션 기반 폼 로그인",
            description = "Form Data 형식으로 이메일과 비밀번호를 전송합니다.",
            // 함수 파라미터가 아니라 @Operation에 requestBody 설정을 넣어놔야 form 형식으로 데이터를 받을 수 있음
            requestBody = @RequestBody(
                    description = "로그인 정보",
                    required = true,
                    // content = @Content(...): Swagger에서 이 Request Body의 Content 타입/스키마를 설명하라는 의미
                    content = @Content(
                            // schema = @Schema(implementation = LoginReqDto.class): 해당 form 데이터의 필드 구조는 LoginReqDto를 따라라는 뜻
                            schema = @Schema(implementation = LoginReqDto.class),
                            mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "로그인 실패")
    @PostMapping(
            value = "/auth/login",
            // consumes: 해당 API 메서드가 소비할 수 있는 데이터의 타입을 지정하는 속성
            // 설정된 consumes 값과 클라이언트가 보낸 Content-Type이 일치하지 않으면 415 Unsupported Media Type 에러를 반환함
            // MediaType.APPLICATION_FORM_URLENCODED_VALUE: "application/x-www-form-urlencoded"를 의미
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    void login(LoginReqDto request);

    @Operation(
            summary = "세션 기반 폼 로그아웃",
            description = "로그아웃 작업을 수행하는 API"
    )
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @ApiResponse(responseCode = "401", description = "로그아웃 실패")
    @PostMapping(
            value = "/auth/logout"
    )
    void logout();
}

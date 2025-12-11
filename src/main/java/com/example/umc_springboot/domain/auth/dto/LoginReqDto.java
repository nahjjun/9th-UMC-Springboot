package com.example.umc_springboot.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "로그인 요청 데이터")
@Builder
@Data   // Setter가 있어야 Swagger가 바인딩을 시도한다.
public class LoginReqDto {
    // 해당 변수명을 username으로 안하면 로그인이 안됨!!
    @Schema(description = "이메일", example = "test@example.com")
    private final String username;
    @Schema(description = "비밀번호", example = "i453i453!")
    private final String password;
}

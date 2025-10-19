package com.example.umc_springboot.Domain.User.Controller;


import com.example.umc_springboot.Global.Response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User 관련 API")
public class UserController {

    @Operation(
            summary = "사용자 정보 가져오기",
            description = """
                로그인된 사용자의 정보를 조회합니다.
                
                #️⃣[인증 필요함]
                - 해당 API는 JWT 인증이 필요한 API입니다.
                - Request Header에 "Authorization: Bearer <AccessToken>"을 포함해아합니다.
                
            """
    )
    @GetMapping("")
    public ResponseEntity<GlobalResponse<?>> getUser() {

    }

}

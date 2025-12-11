package com.example.umc_springboot.domain.auth.controller;


import com.example.umc_springboot.domain.auth.dto.LoginReqDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerDocs {

    @Override
    public void login(LoginReqDto request){
    }

    @Override
    public void logout(){}

}

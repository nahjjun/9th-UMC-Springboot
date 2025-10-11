package com.example.umc_springboot.Domain.UserStoreMission.Controller;

import com.example.umc_springboot.Domain.UserStoreMission.Service.UserStoreMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserStoreMissionController {
    private final UserStoreMissionService userStoreMissionService;


}

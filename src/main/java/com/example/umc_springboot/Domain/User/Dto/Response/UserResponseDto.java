package com.example.umc_springboot.Domain.User.Dto.Response;


import com.example.umc_springboot.Domain.User.Enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserResponseDto {
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final String email;
    private final String point;
}

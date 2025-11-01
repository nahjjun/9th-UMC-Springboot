package com.example.umc_springboot.domain.user.dto.response;


import com.example.umc_springboot.domain.address.entity.Address;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@JsonFilter("UserInfoFilter") // 사용자 정보를 조회할 때 사용할 필터 지정
public class UserInfoResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String gender;
    private LocalDate birth;
    private String phoneNumber;
    private String email;
    private Integer point;
    private Address address;
}

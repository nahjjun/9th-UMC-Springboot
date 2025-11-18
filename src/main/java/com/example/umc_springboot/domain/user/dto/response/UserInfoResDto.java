package com.example.umc_springboot.domain.user.dto.response;


import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.user.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@JsonFilter("UserInfoFilter") // 사용자 정보를 조회할 때 사용할 필터 지정
public class UserInfoResDto {
    private Long id;
    private String name;
    private String nickname;
    private Gender gender;
    private LocalDate birth;
    private String phoneNumber;
    private String email;
    private Integer point;
    private City city;
    private District district;
    private Dong dong;
    private String detail;
}

package com.example.umc_springboot.domain.user.dto.request;

import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.user.enums.Gender;
import com.example.umc_springboot.global.annotation.ExistFoods;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class JoinRequestDto {
    @NotBlank(message = "이름은 비워둘 수 없습니다.")
    @Size(min=3, max=20, message="이름은 3자 ~ 20자로 지어야 합니다.")
    private final String name;

    @NotBlank(message="닉네임은 비워둘 수 없습니다.")
    @Size(min=3, max=20, message="닉네임은 3자 ~ 20자로 지어야 합니다.")
    private final String nickname;

    @NotBlank(message="비밀번호는 비워둘 수 없습니다.")
    private final String password;

    @Builder.Default
    private final Gender gender = Gender.NONE;

    @NotNull(message="생년월일은 비워둘 수 없습니다.")
    private final LocalDate birth;

    @NotBlank(message="전화번호는 비워둘 수 없습니다.")
    private final String phoneNumber;

    @NotBlank(message="이메일은 비워둘 수 없습니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private final String email;

    @NotNull(message="도시는 비워둘 수 없습니다.")
    private final City city;

    @NotNull(message="구는 비워둘 수 없습니다.")
    private final District district;

    @NotNull(message="동은 비워둘 수 없습니다.")
    private final Dong dong;

    @NotBlank(message="상세 주소는 비워둘 수 없습니다.")
    @Size(max=40, message = "상세 주소는 40자 이하로 작성하여야 합니다.")
    private final String detail;

    @NotNull
    @Size(min=3, message = "사용자가 선호하는 읍식점은 최소 3개 이상 골라야 합니다.")
    @ExistFoods
    private final List<String> foodTypes;
}

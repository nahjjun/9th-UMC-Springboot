package com.example.umc_springboot.domain.store.dto.request;

import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStoreReqDto(
        @NotBlank(message = "가게 사장의 고유 식별 번호를 입력해야합니다.")
        String bossUniqueNum,
        @NotBlank(message = "가게 이름을 입력해야합니다.")
        String name,
        @NotBlank(message = "가게 종류를 입력해야합니다.")
        String type,
        String photoUrl,
        @NotNull(message = "'시'를 입력해야합니다.")
        City city,
        @NotNull(message = "'구'를 입력해야합니다.")
        District district,
        @NotNull(message = "'동'을 입력해야합니다.")
        Dong dong,
        @NotBlank(message = "'상세 주소'를 입력해야합니다.")
        String detail
) {
}

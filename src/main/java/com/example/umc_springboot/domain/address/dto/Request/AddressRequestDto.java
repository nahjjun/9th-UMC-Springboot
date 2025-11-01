package com.example.umc_springboot.domain.address.dto.Request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRequestDto {
    private final String city;
    private final String district;
    private final String dong;
    private final String detail;
}

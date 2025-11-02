package com.example.umc_springboot.domain.address.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponseDto {
    private final String city;
    private final String district;
    private final String dong;
    private final String detail;
}

package com.example.umc_springboot.domain.address.dto.Response;

import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResDto {
    private final City city;
    private final District district;
    private final Dong dong;
    private final String detail;
}

package com.example.umc_springboot.domain.address.mapper;


import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.dto.Request.AddressReqDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    /**
     * UserService에서 사용되는 Address 매핑용 DTO인 JoinRequestAddressDto를 Address로 변환하는 함수
     * @param dto
     * @return : Address 객체
     */
    public Address toAddress(AddressReqDto dto){
        return Address.builder()
                .city(dto.getCity())
                .district(dto.getDistrict())
                .dong(dto.getDong())
                .detail(dto.getDetail())
                .build();
    }


}

package com.example.umc_springboot.domain.store.mapper;

import com.example.umc_springboot.domain.address.dto.Request.AddressReqDto;
import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.mapper.AddressMapper;
import com.example.umc_springboot.domain.boss.entity.Boss;
import com.example.umc_springboot.domain.store.dto.request.CreateStoreReqDto;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.enums.StoreType;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreMapper {
    /**
     * 가게를 신규 등록할 때 CreateStoreReqDto -> Store로 변환하는 함수
     * @param dto
     * @return
     */
    public Store toEntity(CreateStoreReqDto dto, Address address, Boss boss){

        // 1. Store 생성
        return Store.builder()
                .boss(boss)
                .name(dto.name())
                .type(StoreType.valueOf(dto.type()))
                .photoUrl(dto.photoUrl())
                .address(address)
                .build();
    }

}

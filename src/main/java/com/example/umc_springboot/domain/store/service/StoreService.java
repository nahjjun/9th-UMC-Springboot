package com.example.umc_springboot.domain.store.service;

import com.example.umc_springboot.domain.address.dto.Request.AddressReqDto;
import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.mapper.AddressMapper;
import com.example.umc_springboot.domain.address.repository.AddressRepository;
import com.example.umc_springboot.domain.boss.entity.Boss;
import com.example.umc_springboot.domain.boss.exception.BossErrorCode;
import com.example.umc_springboot.domain.boss.respository.BossRepository;
import com.example.umc_springboot.domain.store.dto.request.CreateStoreReqDto;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.store.enums.StoreType;
import com.example.umc_springboot.domain.store.exception.StoreErrorCode;
import com.example.umc_springboot.domain.store.mapper.StoreMapper;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.example.umc_springboot.global.util.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final BossRepository bossRepository;
    private final StoreMapper storeMapper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public void createStore(CreateStoreReqDto dto){
        // 1. Boss 가져오기
        Boss boss = bossRepository.findByUniqueNum(dto.bossUniqueNum()).orElseThrow(() -> new CustomException(BossErrorCode.BOSS_NOT_FOUND));

        // 2. StoreType 검증하기
        if(!EnumUtil.isInEnum(StoreType.class, dto.type())){
            throw new CustomException(StoreErrorCode.STORE_TYPE_NOT_MATCH);
        }

        // 3. Address 생성
        AddressReqDto addressReqDto = AddressReqDto.builder()
                .city(dto.city())
                .district(dto.district())
                .dong(dto.dong())
                .detail(dto.detail())
                .build();

        Address address = addressMapper.toAddress(addressReqDto);

        // 4. address 저장
        addressRepository.save(address);

        // 5. Store 생성하기
        Store store = storeMapper.toEntity(dto, address, boss);

        // 6. Store 저장하기
        storeRepository.save(store);
    }

}

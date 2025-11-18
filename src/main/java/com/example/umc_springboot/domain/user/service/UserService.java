package com.example.umc_springboot.domain.user.service;


import com.example.umc_springboot.domain.address.dto.Request.AddressReqDto;
import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.mapper.AddressMapper;
import com.example.umc_springboot.domain.address.repository.AddressRepository;
import com.example.umc_springboot.domain.foodType.entity.FoodType;
import com.example.umc_springboot.domain.foodType.repository.FoodTypeRepository;
import com.example.umc_springboot.domain.user.dto.request.JoinReqDto;
import com.example.umc_springboot.domain.user.dto.response.UserInfoResDto;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.mapper.UserMapper;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.example.umc_springboot.global.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final PasswordUtil passwordUtil;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        // 1. 해당 사용자의 데이터가 이미 있는지 확인하기(이메일 기준)
        if(userRepository.existsByEmail(joinReqDto.getEmail())){
            throw new CustomException(UserErrorCode.EMAIL_OVERLAPPED);
        }
        // 2. 비밀번호 형식이 맞는지 확인
        if(!passwordUtil.isValidPassword(joinReqDto.getPassword())){
            throw new CustomException(UserErrorCode.PASSWORD_OUT_OF_FORM);
        }

        // 1. Address 생성
        Address address = addressMapper.toAddress(
                AddressReqDto.builder()
                        .city(joinReqDto.getCity())
                        .district(joinReqDto.getDistrict())
                        .dong(joinReqDto.getDong())
                        .detail(joinReqDto.getDetail())
                        .build()
        );
        address = addressRepository.save(address);

        // 3. User 생성
        User user = userMapper.toEntity(joinReqDto, address);
        System.out.println("사용자 생성 완료");

        // 4. FoodType List 조회
        List<FoodType> foodTypes = new ArrayList<>();
        joinReqDto.getFoodTypes().forEach(name -> {
            foodTypes.add(foodTypeRepository.findByName(name).getFirst());
        });

        // 5. 조회된 FoodType 리스트로 user에 적용
        foodTypes.forEach(foodType -> {
            user.addFoodType(foodType);
        });

        // 6. User save
        User savedUser = userRepository.save(user);
    }

    /**
     * 사용자 정보를 반환하는 함수
     * @param userId : 사용자 고유 ID
     * @return UserInfoResDto : 사용자 정보 Response Dto
     */
    @Transactional
    public UserInfoResDto getUserInfo(Long userId){
        // 1. userId Null인지 체크
        if(userId == null){
            throw new CustomException(UserErrorCode.USER_ID_IS_NULL);
        }
        // 2. User 정보 조회
        Optional<User> optional = userRepository.findById(userId);
        User user = optional.orElse(null);
        // 사용자를 못찾은 경우, 404 에러 발생
        if(user == null){
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        System.out.println("사용자 정보 조회 완료");
        return userMapper.toUserInfoResponseDto(user);
    }

}

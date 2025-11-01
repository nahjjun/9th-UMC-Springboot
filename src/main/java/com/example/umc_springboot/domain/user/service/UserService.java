package com.example.umc_springboot.domain.user.service;


import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.repository.AddressRepository;
import com.example.umc_springboot.domain.user.dto.request.JoinRequestDto;
import com.example.umc_springboot.domain.user.dto.response.UserInfoResponseDto;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.mapper.UserMapper;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.global.exception.CustomException;
import com.example.umc_springboot.global.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordUtil passwordUtil;
    private final UserMapper userMapper;

    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        // 1. 해당 사용자의 데이터가 이미 있는지 확인하기(이메일 기준)
        if(userRepository.existsByEmail(joinRequestDto.getEmail())){
            throw new CustomException(UserErrorCode.EMAIL_OVERLAPPED);
        }
        // 2. 비밀번호 형식이 맞는지 확인
        if(!passwordUtil.isValidPassword(joinRequestDto.getPassword())){
            throw new CustomException(UserErrorCode.PASSWORD_OUT_OF_FORM);
        }

        // 3. User 생성
        User user = userMapper.toEntity(joinRequestDto);
        System.out.println("사용자 생성 완료");

        // 4. Address, User save
        Address savedAddress = addressRepository.save(user.getAddress());
        User savedUser = userRepository.save(user);
    }

    /**
     * 사용자 정보를 반환하는 함수
     * @param userId : 사용자 고유 ID
     * @return UserInfoResponseDto : 사용자 정보 Response Dto
     */
    @Transactional
    public UserInfoResponseDto getUserInfo(Long userId){
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
        return userMapper.toUserInfoResponseDto(user);
    }

}

package com.example.umc_springboot.domain.user.mapper;

import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.mapper.AddressMapper;
import com.example.umc_springboot.domain.address.repository.AddressRepository;
import com.example.umc_springboot.domain.address.dto.Request.AddressRequestDto;
import com.example.umc_springboot.domain.user.dto.request.JoinRequestDto;
import com.example.umc_springboot.domain.user.dto.response.UserInfoResponseDto;
import com.example.umc_springboot.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    // BCrypt 해싱 함수를 사용해서 비밀번호를 인코딩할 수 있음
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자가 회원가입하는데 필요한 데이터를 받아서 User Entity로 변환해주는 함수
     * @param joinRequestDto : 회원가입 시 클라이언트에게 받는 사용자 정보가 담긴 Dto
     * @return
     */
    public User toEntity(JoinRequestDto joinRequestDto) {
        // 1. Address 생성
        Address address = addressMapper.toAddress(
                AddressRequestDto.builder()
                        .city(joinRequestDto.getCity())
                        .district(joinRequestDto.getDistrict())
                        .dong(joinRequestDto.getDong())
                        .detail(joinRequestDto.getDetail())
                        .build()
        );


        System.out.println("User를 위한 Address 생성 완료");

        User user = User.builder()
                .name(joinRequestDto.getName())
                .nickname(joinRequestDto.getNickname())
                .password(passwordEncoder.encode(joinRequestDto.getPassword()))
                .gender(joinRequestDto.getGender()) // Enum으로 변환해서 생성
                .birth(joinRequestDto.getBirth())
                .phoneNumber(joinRequestDto.getPhoneNumber())
                .email(joinRequestDto.getEmail())
                .address(address)
                .build();

        // User - Address 양방향 매핑
        user.getAddress().getUserList().add(user);
        return user;
    }

    /**
     * User Entity를 사용자 정보 조회 응답 Dto로 변환하는 함수
     * @param user : User Entity
     * @return UserInfoResponseDto : 사용자 정보를 담고 있는 응답 Dto
     */
    public UserInfoResponseDto toUserInfoResponseDto(User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birth(user.getBirth())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .point(user.getPoint())
                .city(user.getAddress().getCity())
                .district(user.getAddress().getDistrict())
                .dong(user.getAddress().getDong())
                .detail(user.getAddress().getDetail())
                .build();
    }

}

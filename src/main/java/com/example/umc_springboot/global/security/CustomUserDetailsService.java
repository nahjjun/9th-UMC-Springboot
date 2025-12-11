package com.example.umc_springboot.global.security;

import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.domain.user.exception.UserErrorCode;
import com.example.umc_springboot.domain.user.repository.UserRepository;
import com.example.umc_springboot.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * UserDetailService는 사용자 정보가 담긴 UserDetail을 찾아서 반환하는 클래스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * username 파라미터를 "email"로 사용한다 (로그인할 때는 이메일로 로그인한다.)
     * AuthenticationProvider가 해당 페서드를
     * @param username
     * @return UserDetails 사용자 정보가 담긴 객체
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // 1. 검증할 User 조회
        User user = userRepository.findByEmail(username).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        // 2. CustomUserDetails 만들어서 반환
        return new CustomUserDetails(user);
    }
}

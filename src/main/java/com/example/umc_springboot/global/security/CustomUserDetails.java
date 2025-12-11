package com.example.umc_springboot.global.security;

import com.example.umc_springboot.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/** UserDetails
 * 인증된 사용자의 아이디, 비밀번호, 권한 등을 포함하는 인터페이스
 * 인증된 사용자의 정보를 Security Context에 보관할 때 사용됨
 *  => Security Context: 로그인 후 인증된 사용자의 정보들을 저장하는 인터페이스
 */

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;


    /**
     * User의 권한(Role)을 꺼내서 Spring Security 전용 권한 객체(SimpleGrantedAuthority)로 변환하는 함수
     * SimpleGrantedAuthority :
     *      Spring Security에서 사용자의 권한(Authority)을 나타내는 가장 기본적인 구현체
     *      "표준 권한 객체": Spring Security 내부에서 권한 검사(인가)를 수행할 때 사용하는 표준화된 객체
     *      GrantedAuthority 인터페이스를 구현하며, 해당 인터페이스는 "getAuthority()"르 갖는다. (권한의 이름을 문자열로 반환하는 함수)
     * @return List<SimpleGrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        // 1. 해당 사용자가 갖고 있는 Role을 추출 (한명의 사용자가 여러 권한을 가질 수 있으므로, List로 가져오기)
        // 여기서는 사용자가 1개의 권한을 가지므로 아래처럼 구현
        List<String> roles = List.of(user.getRole().toString());

        // 2. 각 String을 SimpleGrantedAuthority 객체로 변환한다.
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * 인증 시 비교할 사용자 비밀번호 반환
     * @return
     */
    @Override
    public String getPassword(){
        return user.getPassword();
    }

    /**
     * 인증 시 로그인 식별자 반환
     * 로그인은 이메일로 진행할 것이므로, 사용자 이메일을 반환
     * @return
     */
    @Override
    public String getUsername(){
        return user.getEmail();
    }




}

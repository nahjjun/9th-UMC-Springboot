package com.example.umc_springboot.domain.auth.enums;


public enum Role {
    // SecurityConfig의 "hasRole()" 메서드를 사용할 때, DB에 "ROLE_" 접두사가 붙어있어야 함
    ROLE_ADMIN,
    ROLE_USER,
}

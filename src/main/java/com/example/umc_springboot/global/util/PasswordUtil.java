package com.example.umc_springboot.global.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    /**
     * 비밀번호가 형식에 맞는지 확인하는 함수
     * 8~12 자리 & 영문 & 특수문자 1개 이상 포함
     * @param password 검사할 비밀번호 문자열
     * @return 규칙에 맞으면 true, 규칙에 맞지 않으면 false
     */
    public boolean isValidPassword(String password){
        // 1. 비밀번호 길이 체크
        if(password == null || password.length() < 8 || password.length() > 16){
            return false;
        }

        // 2. 정규식으로 체크
        String regex = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,16}$";
        // (?=.*[A-Za-z]) : 영문자가 최소 한 글자 이상 포함
        // (?=.*[!@#$%^&*(),.?":{}|<>]) : 특수문자가 최소 한개 이상 포함

        return password.matches(regex);
    }

}

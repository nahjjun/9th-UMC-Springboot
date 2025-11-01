package com.example.umc_springboot.global.util;

public class EnumUtil {
    /**
     * String을 Enum으로 바꾸는 함수
     * @param value : Enum으로 바꾸고자 하는 변수
     * @param enumClass : 바꾸고자 하는 Enum의 Class
     * @param field : 오류 메시지를 정확하게 하고자 받는 필드 변수명
     * @return : 바꾼 Enum Class 객체
     * @param <E>
     */
    public static <E extends Enum<E>> E toEnum(Class<E> enumClass, String value, String field) {
        if(value == null) throw new IllegalArgumentException(field + " 가 null입니다.");
        value = value.toUpperCase(); // 대문자로 변환
        try{
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(field + " has Invalid Value: " + value);
        }
    }
}

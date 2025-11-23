package com.example.umc_springboot.global.annotation;

import com.example.umc_springboot.global.validator.PageZeroValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageZeroValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageZero {
    // 검증 실패 시 기본으로 쓰일 에러 메시지 정의
    String message() default "0 이하인 페이지는 지정할 수 없습니다.";
    /* 검증 그룹용
        - bean validation에서 제공하는 검증 그룹 기능을 위한 필드
        - 일반적인
     */
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

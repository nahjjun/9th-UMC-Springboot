package com.example.umc_springboot.global.annotation;

import com.example.umc_springboot.global.validator.FoodExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/* 사용자 정의 어노테이션 만들 때 붙이는 어노테이션
    -> 이 어노테이션이 붙은 커스텀 어노테이션이 JavaDoc에 노출되도록 해주는 메터 어노테이션
* */
@Documented

/* 사용자가 validation을 커스텀 어노테이션을 통해 할 수 있도록 재공하는 어노테이션
    -> validatedBy로 지정해준 FoodExistValidator라는 클래스를 통해 @ExistFoods가 붙은 대상을 검증함
    -> Bean Validation에서 사용하는 제약 조건이다라고 선언하는 어노테이션(검증 클래스 지정)
*/
@Constraint(validatedBy = FoodExistValidator.class)

/* 어노테이션의 적용 범위 지정
    -> 해당 어노테이션을 어디에 붙일 수 있을지 지정하는 설정
*/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})

// 어노테이션의 생명 주기 지정. RUNTIME이 실행되는 동안만 어노테이션이 유효하도록 설정
@Retention(RetentionPolicy.RUNTIME) 
public @interface ExistFoods {
    // 검증 실패 시 기본으로 쓰일 에러 메시지 정의
    String message() default "해당 음식이 존재하지 않습니다.";
    /* 검증 그룹용
        - bean validation에서 제공하는 검증 그룹 기능을 위한 필드
        - 일반적인
     */
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

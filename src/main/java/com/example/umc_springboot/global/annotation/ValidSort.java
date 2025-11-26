package com.example.umc_springboot.global.annotation;


import com.example.umc_springboot.global.validator.ValidSortValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidSortValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSort {
    /* 검증 실패 시 기본으로 쓰일 에러 메시지 정의
     @Valid, @Validated, @RequestBody, @ModelAttribute 같은 검증 대상에서 해당 메시지가 클라이언트 응답으로 포함될 수 있다.
     어노테이션에서 message 속성으로 메시지 내용 오버라이드도 가능함
     */
    String message() default "페이징 처리할 때 지정해줄 sort String 형식이 옳지 않습니다.";


    /**
     * 정렬에 사용할 대상 클래스
      */
    Class<?> target();

    /**
     * "필드명,정렬방식" 형식이 잘못되었을 때 사용할 메시지
     */
    String invalidSortFormatMessage() default "정렬 형식은 '필드명,정렬방식'이어야 합니다.";

    /**
     * 필드는 존재하지 않지만, 형식은 맞을 때 사용할 메시지
     * %s 자리에 실제 필드명이 들어가게 사용할 예정
     */
    String unknownFieldMessage() default "정렬 대상 필드가 존재하지 않습니다. 필드명=%s";

    /*
    * Class<?>[] groups()
    * Validation Groups 기능을 사용할 때 그룹을 지정하기 위한 필드
    * Validation Groups는 특정 상황에서만 검증을 적용하고 싶을 때 사용한다.
    * */
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

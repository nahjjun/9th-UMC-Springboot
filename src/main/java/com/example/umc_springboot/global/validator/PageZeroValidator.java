package com.example.umc_springboot.global.validator;

import com.example.umc_springboot.global.annotation.PageZero;
import com.example.umc_springboot.global.exception.GlobalErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
/* ContraintValidator<A, T>:
    - A: 어떤 어노테이션을 검증하는 지 지정
        -> ExistFoods
    - T: 어떤 타입을 검증하는지 지정
        -> List<String>
 */
public class PageZeroValidator implements ConstraintValidator<PageZero, Integer> {
    // 인자로 들어온 Pageable의 page가 0보다 크거나 같은지 확인
    @Override
    public boolean isValid(Integer num, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid =  num > 0;
        if(!isValid){
            // @PageSort에서 설정된 Default 메시지를 초기화시키고, 새로운 메시지로 덮어씌운다
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(GlobalErrorCode.PAGE_SORT_STYLE_WRONG.getMessage()).addConstraintViolation();
        }
        return isValid;
    }
}

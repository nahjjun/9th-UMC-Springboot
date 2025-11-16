package com.example.umc_springboot.global.validator;

import com.example.umc_springboot.domain.foodType.entity.FoodType;
import com.example.umc_springboot.domain.foodType.exception.FoodTypeErrorCode;
import com.example.umc_springboot.domain.foodType.repository.FoodTypeRepository;
import com.example.umc_springboot.global.annotation.ExistFoods;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
/* ContraintValidator<A, T>:
    - A: 어떤 어노테이션을 검증하는 지 지정
        -> ExistFoods
    - T: 어떤 타입을 검증하는지 지정
        -> List<String>
 */
public class FoodExistValidator implements ConstraintValidator<ExistFoods, List<String>> {
    private final FoodTypeRepository foodTypeRepository;

    //  FoodType String 기준으로 valid 진행하는 함수
    @Override
    public boolean isValid(List<String> foodTypes, ConstraintValidatorContext context){
        boolean isValid = foodTypes.stream()
                .allMatch(type->foodTypeRepository.existsByName(type));
            // => allMath(Predicate): 스트림의 모든 요소가 조건(predicate)을 만족하면 true, 하나라도 만족 안하면 false
        if (!isValid) {
            // 이 부분에서 아까 디폴트 메시지를 초기화 시키고, 새로운 메시지로 덮어씌우게 됩니다.
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FoodTypeErrorCode.FOOD_TYPE_ERROR.getMessage()).addConstraintViolation();
        }
        return isValid;
    }
}

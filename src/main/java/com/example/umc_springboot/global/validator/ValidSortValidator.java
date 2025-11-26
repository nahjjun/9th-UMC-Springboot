package com.example.umc_springboot.global.validator;


import com.example.umc_springboot.global.annotation.ValidSort;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
/* ContraintValidator<A, T>: 사용자가 새로운 검증 규칙을 만들고 싶을 때 반드시 구현해야하는 인터페이스
    - A: 어떤 어노테이션을 검증하는 지 지정
        -> ExistFoods
    - T: 어떤 타입을 검증하는지 지정
        -> List<String>
 */
public class ValidSortValidator implements ConstraintValidator<ValidSort, String> {
    private Class<?> targetType;
    private String invalidSortFormatMessage;    // sort string 형식이 잘못되었을 때 나오는 에러 메시지
    private String unknownFieldMessage;         // 필드명이 없는 경우 나오는 에러 메시지

    /** 캐시 저장소 - (Key: 클래스 정보, Value: 해당 클래스가 가진 모든 필드명 목록)
     *  static final로 선언하여, 어플리케이션 전체에서 내부적으로만 공유함
     *  멀티스레드 환경(웹 서버)에서 안전한 ConcurrentHashMap 사용
    */
    private static final Map<Class<?>, Set<String>> fieldCache = new ConcurrentHashMap<>();

    /**
     * 어노테이션의 속성값을 Validator에게 전달하기 위해 사용되는 초기화 메서드
     * => Bean validation이 Validator 객체를 생성할 때 자동으로 호출되는 생명주기 메서드이므로 override한다.
     * => Target의 속성값(target class, 커스텀 메시지 등)을 validator 내부에서 사용하려면 반드시 initialize()를 통해 전달받아야 한다.
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ValidSort constraintAnnotation) {
        this.targetType = constraintAnnotation.target();
        this.invalidSortFormatMessage = constraintAnnotation.invalidSortFormatMessage();
        this.unknownFieldMessage = constraintAnnotation.unknownFieldMessage();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        // 1. null 또는 공백은 "정렬 기준 미지정"으로 간주하고 통과
        if(value == null || value. isBlank()){
            return true;
        }

        // 1. 입력으로 들어온 Sort String이 "필드명,정렬기준" 형식인지 검사
        String[] parts = value.split(",");
        // "," 기준으로 분리한 문자열이 2가 아닌 경우, 입력 형식이 잘못된 것이므로, 그에 맞는 메시지 설정
        if (parts.length != 2){
            setDefaultMessageToCustomMessage(context, invalidSortFormatMessage);
            return false;
        }

        // 2. 분리한 필드명, 정렬 기준 할당
        String fieldName = parts[0].trim();
        String direction = parts[1].trim().toLowerCase(); // 소문자로 변환

        // 3. 정렬 기준(direction) asc/desc 중 하나인지 확인
        // 정렬 기준이 asc, desc 둘 다 아니면 형식 오류로 간주한다.
        if(!direction.equals("asc") && !direction.equals("desc")){
            setDefaultMessageToCustomMessage(context, invalidSortFormatMessage);
            return false;
        }

        // 4. 필드 존재 여부 체크
        if(!hasField(this.targetType, fieldName)){
            setDefaultMessageToCustomMessage(context, unknownFieldMessage);
            return false;
        }
        return true;
    }


    /**
*    * 해당 Class Type에서 부모로 올라가며 해당하는 필드명이 있는지 확인하는 함수
     * @param type
     * @param fieldName
     * @return
     */
    private boolean hasField(Class<?> type, String fieldName){
        // 1. 캐시 조회 및 생성 (지연 로딩 방식으로 캐시 채움)
        // type이라는 class의 key가 fieldCache안에 없다면, 아직 해당 캐시가 불러와지지 않은 것이므로 캐시를 채운다.
        // 어플리케이션이 시작될 때 모든 클래스의 필드들을 즉시 가져오지 않고, 각각의 key가 사용될 때 캐시를 채우는 지연 방식이다.
        Set<String> fields = fieldCache.computeIfAbsent(type, this::getAllFields);
            // -> absent: 불참, 없는
            // hashMap.computeIfAbsent():
                // 맵에 특정 키가 없을 때만 주어진 함수를 실행해 값을 계산하고, 그 결과를 맵에 Key:value로 저장한 뒤 반환하는 메서드
                // 이미 기존 값이 존재하면, 기존 값을 그대로 반환함
            // hashMap.computeIfAbsent(): 키가 이미 존재할 때만 주어진 함수를 실행해서 새로 계산한 결과를 덮어씌우는 메서드

        // 2. O(1) 속도로 즉시 확인
        return fields.contains(fieldName);
    }

    // 비싼 작업인 자바 리플렉션을 활용하여, 부모들을(BaseEntity까지) 뒤져서 모든 필드명들을 수집하는 함수
    private Set<String> getAllFields(Class<?> type){
        Set<String> fields = new HashSet<>();
        Class<?> current = type;

        // 전달받은 Entity Class와 해당 클래스의 부모인 BaseTimeEntity는 둘다 Object.class를 상속받기 때문에, 이렇게 지정해주면 BaseTimeEntity까지 검사할 수 있음 
        while(current != null && current != Object.class){
            // .getDeclaredFields(): 현재 해당 클래스가 갖고 있는 필드들을 모두 가져옴(상속된거 빼고 오직 본인거만 가져옴)
            for(Field field : current.getDeclaredFields()){
                fields.add(field.getName());
            }
            current = current.getSuperclass();
        }
        return fields;
    }

    /**
     * 기본 메시지를 제거하고, 커스텀 메시지로 덮어씌우는 함수
     * @param context
     * @param message
     */
    private void setDefaultMessageToCustomMessage(ConstraintValidatorContext context, String message){
        context.disableDefaultConstraintViolation(); // default 메시지 삭제
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation(); // 새로운 ConstraintViolation 생성해서 추가
    }

}

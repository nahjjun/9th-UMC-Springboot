package com.example.umc_springboot.domain.user.entity;


import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.foodType.entity.FoodType;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.user.enums.Gender;
import com.example.umc_springboot.domain.user.enums.UserStatus;
import com.example.umc_springboot.domain.userFoodType.entity.UserFoodType;
import com.example.umc_springboot.domain.userProvision.entity.UserProvision;
import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
// Private로 막아두면 외부에서 직접 호출할 수 없음
// 즉, 외부 코드에서 new User(...)로 생성하는 것을 금지함 (무분별한 직접 생성 금지, 정적 팩토리 메서드 전용)
@AllArgsConstructor(access = AccessLevel.PRIVATE)

// Protected로 설정하면 JPA/Hibernate는 접근 가능하지만, 개발자가 직접 호출하지는 못하게 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "User")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT와 같음
    private Long id;

    @Column(name = "name",  nullable = false, length = 10)
    private String name;

    @Column(name = "nickname",  nullable = false, length = 20)
    private String nickname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "gender",  nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NONE;

    @Column(name = "birth",  nullable = false)
    private LocalDate birth;

    @Column(name = "phone_number",  nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email",  nullable = false, length = 40)
    private String email;

    @Column(name = "point",  nullable = false)
    @Builder.Default
    private Integer point = 0;

    @Column(name="status", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name="inactive_date")
    private LocalDate inactiveDate;

    /* CascadeType 설정
       * REMOVE: user가 삭제되거나 userStoreMissionList에서 특정 객체가 제거되면, 해당 테이블에서도 제거되도록 설정 (delete 전파)
       * PERSIST: 부모 save() 시 자식도 자동 persist (insert 전파)
       * MERGE: 부모 merge() 시 자식도 merge (update 전파)
       * REFRESH: 부모 refresh() 시 자식도 refresh (db 값으로 초기화)
       * ALL: 위 모든 옵션 포함 (가장 일반적으로 사용됨)
    * */ 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    private List<UserStoreMission> userStoreMissionList = new ArrayList<>();
    
        
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    private List<UserFoodType> userFoodTypeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    private List<UserProvision> userProvisionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    private Review review;

    /* FoodType을 입력 받아서 UserFoodType을 생성한 뒤 userFoodTypeList에 추가하는 메서드
        -> Cascade 설정을 all로 해두면, list에 추가한 후 commit하면 최종적으로 적용이 된다.
    * */
    public void addFoodType(FoodType foodType) {
        UserFoodType userFoodType = UserFoodType.builder()
                .user(this)
                .foodType(foodType)
                .build();
        userFoodTypeList.add(userFoodType);
    }
}

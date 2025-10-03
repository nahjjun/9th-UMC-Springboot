package com.example.umc_springboot.Domain.User.Entity;


import com.example.umc_springboot.Domain.User.Enums.Gender;
import com.example.umc_springboot.Domain.User.Enums.UserStatus;
import com.example.umc_springboot.Domain.UserFoodType.Entity.UserFoodType;
import com.example.umc_springboot.Domain.UserProvision.Entity.UserProvision;
import com.example.umc_springboot.Domain.UserStoreMission.Entity.UserStoreMission;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "nickname",  nullable = false, length = 10)
    private String nickname;

    @Column(name = "gender",  nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NONE;

    @Column(name = "birth",  nullable = false)
    private LocalDate birth;

    @Column(name = "address",  nullable = false, length = 30)
    private String address;

    @Column(name = "phone_number",  nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email",  nullable = false, length = 20)
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

    @OneToMany(mappedBy = "user")
    private Set<UserStoreMission> userStoreMissionSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserFoodType> userFoodTypeSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserProvision> userProvisionSet = new HashSet<>();
}

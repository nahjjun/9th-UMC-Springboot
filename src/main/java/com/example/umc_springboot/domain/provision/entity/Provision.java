package com.example.umc_springboot.domain.provision.entity;

import com.example.umc_springboot.domain.provision.enums.ProvisionProperty;
import com.example.umc_springboot.domain.userProvision.entity.UserProvision;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "Provision")
public class Provision extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT와 같음
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @Column(name = "property", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProvisionProperty property;

    // cascade와 orphanremover 설정 안해놓은 이유: 나중에 약관이 바뀌어도 법적 공방에서 해당 약관 동의 기록이 사용될 경우가 있기 때문임.
    // 따라서, Provision과 UserProvision에서는 soft delete를 하는 것이 좋다. (향후 해당 API 짤 때 soft delete 가능하도록 리팩토링 하기!!)
    @OneToMany(mappedBy = "provision")
    @Builder.Default
    private List<UserProvision> userProvisionList = new ArrayList<>();
}

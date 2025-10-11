package com.example.umc_springboot.Domain.Provision.Entity;

import com.example.umc_springboot.Domain.Provision.Enums.ProvisionProperty;
import com.example.umc_springboot.Domain.UserProvision.Entity.UserProvision;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "provision")
    private List<UserProvision> userProvisionList = new ArrayList<>();
}

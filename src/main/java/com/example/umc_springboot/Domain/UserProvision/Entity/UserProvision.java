package com.example.umc_springboot.Domain.UserProvision.Entity;

import com.example.umc_springboot.Domain.Provision.Entity.Provision;
import com.example.umc_springboot.Domain.User.Entity.User;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
// Private로 막아두면 외부에서 직접 호출할 수 없음
// 즉, 외부 코드에서 new User(...)로 생성하는 것을 금지함 (무분별한 직접 생성 금지, 정적 팩토리 메서드 전용)
@AllArgsConstructor(access = AccessLevel.PRIVATE)

// Protected로 설정하면 JPA/Hibernate는 접근 가능하지만, 개발자가 직접 호출하지는 못하게 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "UserProvision")
public class UserProvision extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT와 같음
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provision_id")
    private Provision provision;
}

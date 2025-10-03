package com.example.umc_springboot.Domain.UserStoreMission.Entity;

import com.example.umc_springboot.Domain.StoreMission.Entity.StoreMission;
import com.example.umc_springboot.Domain.User.Entity.User;
import com.example.umc_springboot.Domain.User.Enums.UserStatus;
import com.example.umc_springboot.Domain.UserStoreMission.Enums.UserStoreMissionStatus;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UserStoreMission")
public class UserStoreMission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_mission_id", nullable = false)
    private StoreMission storeMission;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    // 기본은 EnumType.ORDINAL으로 설정되는데, 이렇게 되면 enum의 순서(0,1,2..)를 DB에 저장하게 된다.
    // 따라서, EnumType.STRING으로 설정하여 ENUM의 이름을 DB에 저장하게 하는 것이다.
    @Builder.Default
    private UserStoreMissionStatus status = UserStoreMissionStatus.IN_PROGRESS;

}

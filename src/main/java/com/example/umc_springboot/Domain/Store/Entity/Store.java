package com.example.umc_springboot.Domain.Store.Entity;

import com.example.umc_springboot.Domain.Address.Entity.Address;
import com.example.umc_springboot.Domain.Boss.Entity.Boss;
import com.example.umc_springboot.Domain.Review.Entity.Review;
import com.example.umc_springboot.Domain.Store.Enums.StoreStatus;
import com.example.umc_springboot.Domain.Store.Enums.StoreType;
import com.example.umc_springboot.Domain.StoreMission.Entity.StoreMission;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Store")
public class Store extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fetch : 연관된 엔티티를 언제 로딩할지 결정하는 전략을 지정하는 속성
    // FetchType: JPA에서 연관관게를 맺을 때, 연관된 엔티티를 즉시 가져올지(lazy vs eager)를 설정하는 방식
        // => LAZY : 연관된 엔티티를 실제로 사용할 때까지 로딩을 미룸
        // DB에서 현재 엔티티를 조회할 때는 연관된 엔티티를 가져오지 않고, 해당 필드에 접근하는 순간에 Hibernate가 추가 쿼리로 가져온다.
        // N+1 문제 발생 가능함
        // => EAGER : 엔티티를 조회할 때 연관된 엔티티도 함께 가져온다.
        // 코드가 단순하지만 불필요한 조인/쿼리로 성능 저하 가능함
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boss_id", nullable = false)
    // Store 테이블에 boos_id라는 컬럼이 생기고, 해당 컬럼이 Boss 테이블의 PK를 참조하는 것
    private Boss boss;

    @Column(name="name", nullable = false, length = 10)
    private String name;

    @Column(name="type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreType type;

    @Column(name = "photo_url", length = 30)
    private String photoUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StoreStatus status = StoreStatus.ACTIVE;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true) // 해당 클래스에서 어떤 필드명으로 매핑되어있는지 지정
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade =  CascadeType.REMOVE, orphanRemoval = true)
    private List<StoreMission> storeMissionList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

}

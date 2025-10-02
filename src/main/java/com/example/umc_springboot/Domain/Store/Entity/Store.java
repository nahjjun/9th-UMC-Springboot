package com.example.umc_springboot.Domain.Store.Entity;

import com.example.umc_springboot.Domain.Boss.Entity.Boss;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
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
    @JoinColumn(name="boss_id")
    // Store 테이블에 boos_id라는 컬럼이 생기고, 해당 컬럼이 Boss 테이블의 PK를 참조하는 것
    private Boss boss;

    @Column(name="name", nullable = false, length = 10)
    private String name;




}

package com.example.umc_springboot.domain.boss.entity;

import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "Boss")
public class Boss extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "uniqueNum", nullable = false, length = 6)
    private String uniqueNum;

    /* 이 설정에는 orphanRemoval를 true로 설정하지 않았다! + cascadeType.PERSIST, MERGE 설정
        - 사장이 바뀌는 경우가 생길 수도 있기 때문이다.
        - 사장이 바뀌면 기존 사장의 store List에서는 store가 사라지고, 해당 store의 boss_id는 null값으로 남게 된다.
        - 또한, 사장이 사라지는 경우 가게의 데이터도 사라지는 경우를 대비해서 cascadeType을 persist(저장), merge(수정)할 때만 설정한다.
     */
    @OneToMany(mappedBy = "boss", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Store> store = new ArrayList<>();
}

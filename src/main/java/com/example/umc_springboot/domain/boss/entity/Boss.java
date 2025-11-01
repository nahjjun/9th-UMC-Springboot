package com.example.umc_springboot.domain.boss.entity;

import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "boss")
    private Store store;
}

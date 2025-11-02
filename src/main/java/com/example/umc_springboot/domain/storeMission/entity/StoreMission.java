package com.example.umc_springboot.domain.storeMission.entity;

import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.storeMission.enums.StoreMissionStatus;
import com.example.umc_springboot.domain.userStoreMission.entity.UserStoreMission;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="StoreMission")
public class StoreMission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission; // Mission의 PK와 연결

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "ended_at")
    private LocalDate endedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StoreMissionStatus status = StoreMissionStatus.ACTIVATE;

    @OneToMany(mappedBy = "storeMission", cascade =  CascadeType.REMOVE, orphanRemoval = true)
    private List<UserStoreMission> userStoreMissionList = new ArrayList<>();
}

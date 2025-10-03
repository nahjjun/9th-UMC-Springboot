package com.example.umc_springboot.Domain.Mission.Entity;

import com.example.umc_springboot.Domain.Mission.Enums.MissionModifier;
import com.example.umc_springboot.Domain.Mission.Enums.MissionType;
import com.example.umc_springboot.Domain.StoreMission.Entity.StoreMission;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "Mission")
public class Mission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price_criteria", nullable=false)
    private Integer priceCriteria;

    @Column(name = "modifier", nullable=false)
    @Enumerated(EnumType.STRING)
    private MissionModifier modifier;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MissionType type;

    @Column(name = "point", nullable = false)
    @Builder.Default
    private Integer point = 0;

    @OneToMany(mappedBy = "mission")
    Set<StoreMission> missionSet = new HashSet<>();

}




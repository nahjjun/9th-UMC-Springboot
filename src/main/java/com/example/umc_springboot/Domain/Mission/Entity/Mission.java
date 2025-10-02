package com.example.umc_springboot.Domain.Mission.Entity;

import com.example.umc_springboot.Domain.Mission.Enums.MissionModifier;
import com.example.umc_springboot.Domain.Mission.Enums.MissionType;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


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

    @Column(name = "missionModifier", nullable=false)
    private MissionModifier missionModifier;

    @Column(name = "missionType", nullable = false)
    private MissionType missionType;

    @Column(name = "point", nullable = false)
    @Builder.Default
    private Integer point = 0;

}




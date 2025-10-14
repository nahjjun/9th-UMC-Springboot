package com.example.umc_springboot.Domain.Mission.Entity;

import com.example.umc_springboot.Domain.Mission.Enums.MissionModifier;
import com.example.umc_springboot.Domain.Mission.Enums.MissionType;
import com.example.umc_springboot.Domain.StoreMission.Entity.StoreMission;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    // 미션 자체가 사라지면 storeMission도 사라지도록 설정. mission에서 list의 내용 삭제하면 해당 테이블에서 데이터 사라지게 설정
    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<StoreMission> storeMissionList = new ArrayList<>();

}




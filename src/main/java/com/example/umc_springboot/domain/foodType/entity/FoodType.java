package com.example.umc_springboot.domain.foodType.entity;

import com.example.umc_springboot.domain.userFoodType.entity.UserFoodType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "FoodType")
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    // 음식 타입 행이 사라지면 UserFoodType에 있는 데이터도 삭제되도록 설정.
    @OneToMany(mappedBy = "foodType", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserFoodType> userFoodTypeList = new ArrayList<>();
}

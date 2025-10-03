package com.example.umc_springboot.Domain.FoodType.Entity;

import com.example.umc_springboot.Domain.UserFoodType.Entity.UserFoodType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "foodType")
    private Set<UserFoodType> userFoodTypeSet = new HashSet<>();
}

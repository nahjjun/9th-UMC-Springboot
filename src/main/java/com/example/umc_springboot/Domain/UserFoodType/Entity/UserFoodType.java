package com.example.umc_springboot.Domain.UserFoodType.Entity;

import com.example.umc_springboot.Domain.FoodType.Entity.FoodType;
import com.example.umc_springboot.Domain.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UserFoodType")
public class UserFoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodtype_id",  nullable = false)
    private FoodType foodType;
}

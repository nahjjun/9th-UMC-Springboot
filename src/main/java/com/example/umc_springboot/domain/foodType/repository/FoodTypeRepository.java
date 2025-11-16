package com.example.umc_springboot.domain.foodType.repository;

import com.example.umc_springboot.domain.foodType.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    List<FoodType> findByName(String name);
    boolean existsByName(String name);
}

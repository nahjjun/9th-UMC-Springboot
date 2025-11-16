package com.example.umc_springboot.domain.userFoodType.repository;

import com.example.umc_springboot.domain.userFoodType.entity.UserFoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFoodTypeRepository extends JpaRepository<UserFoodType, Integer> {

}

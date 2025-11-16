package com.example.umc_springboot.domain.userFoodType.service;

import com.example.umc_springboot.domain.userFoodType.repository.UserFoodTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFoodTypeService {
    private final UserFoodTypeRepository userFoodTypeRepository;

}

package com.example.umc_springboot.domain.user.repository;

import com.example.umc_springboot.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}

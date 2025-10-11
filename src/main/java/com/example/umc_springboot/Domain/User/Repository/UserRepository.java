package com.example.umc_springboot.Domain.User.Repository;

import com.example.umc_springboot.Domain.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}

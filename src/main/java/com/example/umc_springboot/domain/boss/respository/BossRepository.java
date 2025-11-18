package com.example.umc_springboot.domain.boss.respository;

import com.example.umc_springboot.domain.boss.entity.Boss;
import com.example.umc_springboot.domain.store.repository.StoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BossRepository extends JpaRepository<Boss, Long> {

    public Optional<Boss> findByUniqueNum(String uniqueNum);
}

package com.example.umc_springboot.domain.store.repository;

import com.example.umc_springboot.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}

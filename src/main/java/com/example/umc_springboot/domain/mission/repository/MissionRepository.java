package com.example.umc_springboot.domain.mission.repository;

import com.example.umc_springboot.domain.mission.entity.Mission;
import com.example.umc_springboot.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {


    Page<Mission> findByStoreIdAndStatus(Long storeId, MissionStatus status, Pageable pageable);

}

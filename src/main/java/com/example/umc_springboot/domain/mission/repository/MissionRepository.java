package com.example.umc_springboot.domain.mission.repository;

import com.example.umc_springboot.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

}

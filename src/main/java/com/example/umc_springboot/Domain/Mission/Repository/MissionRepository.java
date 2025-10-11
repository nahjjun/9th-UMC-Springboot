package com.example.umc_springboot.Domain.Mission.Repository;

import com.example.umc_springboot.Domain.Mission.Entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByMissionId(Long missionId);
}

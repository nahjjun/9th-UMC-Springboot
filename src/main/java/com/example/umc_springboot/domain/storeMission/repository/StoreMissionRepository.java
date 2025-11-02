package com.example.umc_springboot.domain.storeMission.repository;

import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.storeMission.entity.StoreMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StoreMissionRepository extends JpaRepository<StoreMission, Long> {

    @Query("select sm " +
            "from StoreMission sm " +
            "join fetch sm.store s " + // JPQL에서는 sql의 join on과 다르게, 설정해놓은 연관관계를 통해 join한다. 이렇게 하면 자동으로 on 조건이 생성된다.
            "join fetch s.address a " +
            "where a.city=:city and a.district=:district and a.dong=:dong"
    )
    Page<StoreMission> findByCityAndDistrictAndDong(@Param("city") City city, @Param("district") District district, @Param("dong")Dong dongm, Pageable pageable);
}

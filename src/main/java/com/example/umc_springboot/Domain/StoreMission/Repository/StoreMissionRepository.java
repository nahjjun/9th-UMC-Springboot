package com.example.umc_springboot.Domain.StoreMission.Repository;

import com.example.umc_springboot.Domain.Address.Enums.City;
import com.example.umc_springboot.Domain.Address.Enums.District;
import com.example.umc_springboot.Domain.Address.Enums.Dong;
import com.example.umc_springboot.Domain.StoreMission.Entity.StoreMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StoreMissionRepository extends JpaRepository<StoreMission, Long> {

    @Query("select sm " +
            "from StoreMission sm " +
            "join sm.store s " + // JPQL에서는 sql의 join on과 다르게, 설정해놓은 연관관계를 통해 join한다. 이렇게 하면 자동으로 on 조건이 생성된다.
            "join s.address a " +
            "where a.city=:city and a.district=:district and a.dong=:dong"
    )
    Page<StoreMission> findByCityAndDistrictAndDong(@Param("city") City city, @Param("district") District district, @Param("dong")Dong dongm, Pageable pageable);
}

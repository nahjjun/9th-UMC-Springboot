package com.example.umc_springboot.domain.address.repository;

import com.example.umc_springboot.domain.address.entity.Address;
import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
    boolean existsByCityAndDistrictAndDong(City city, District district, Dong dong);
}

package com.example.umc_springboot.domain.address.entity;


import com.example.umc_springboot.domain.address.enums.City;
import com.example.umc_springboot.domain.address.enums.District;
import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city", nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(name = "district", nullable = false)
    @Enumerated(EnumType.STRING)
    private District district;

    @Column(name = "dong", nullable = false)
    @Enumerated(EnumType.STRING)
    private Dong dong;

    @Column(name="detail", length=40, nullable = false)
    private String detail;

    @OneToMany(mappedBy = "address")
    private List<User> userList = new ArrayList<>();

    @OneToOne(mappedBy = "address")
    private Store store;


}

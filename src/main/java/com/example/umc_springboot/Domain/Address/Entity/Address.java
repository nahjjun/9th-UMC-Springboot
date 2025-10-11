package com.example.umc_springboot.Domain.Address.Entity;


import com.example.umc_springboot.Domain.Address.Enums.City;
import com.example.umc_springboot.Domain.Address.Enums.District;
import com.example.umc_springboot.Domain.Address.Enums.Dong;
import com.example.umc_springboot.Domain.Store.Entity.Store;
import com.example.umc_springboot.Domain.User.Entity.User;
import com.example.umc_springboot.Domain.UserProvision.Entity.UserProvision;
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

    @OneToMany(mappedBy = "address")
    private List<User> userList = new ArrayList<>();

    @OneToOne(mappedBy = "address")
    private Store store;
}

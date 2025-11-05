package com.example.umc_springboot.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {
    private final EntityManager entityManager; // JPA 핵심 객체. 영속성 컨텍스트를 관리함

    // JPAQeuryFactory : JPA 환경에서 타입 안전한 쿼리를 쉽게 작성하도록 도와주는 클래스
    // EntityManager를 기반으로 생성되며, 실제 결과를 가져오는 역할도 함
    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}

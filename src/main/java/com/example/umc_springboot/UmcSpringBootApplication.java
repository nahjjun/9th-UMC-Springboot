package com.example.umc_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Spring Data JPA에서 감사(Auditing) 기능을 활성화 하는 어노테이션
// ㄴ> 엔티티가 언제 생성되었는지, 언제 수정했는지, 누가 생성/수정했는 지 등의 메타데이터를 자동으로 기록해주는 기능을 키는 역할
public abstract class UmcSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmcSpringBootApplication.class, args);
    }

}

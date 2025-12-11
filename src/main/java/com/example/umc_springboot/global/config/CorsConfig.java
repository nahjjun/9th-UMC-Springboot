package com.example.umc_springboot.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 클라이언트에서 자격 증명(쿠키, Authorization 헤더 등)을 보낼 수 있도록 허용
        // 세션 인증의 핵심은 쿠키(JSESSIONID)이다.
        config.setAllowCredentials(true);

        // 허용할 origin(출처, 도메인)
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        // 예: React/Vite 개발 서버에서 오는 요청 허용

        // 허용할 HTTP Method
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 클라이언트가 요청 시 보낼 수 있는 헤더 (여기서는 모든 헤더 허용)
        config.setAllowedHeaders(List.of("*"));

        // 서버 응답에서 노출할 헤더 (여기서는 모든 헤더 노출)
        config.setExposedHeaders(List.of("*"));

        // 최종적으로 모든 요청 경로("/**")에 위의 CORS 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

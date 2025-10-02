package com.example.umc_springboot.Global.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    // 인증 없이 접근 가능한 화이트 리스트 URL 모음 String 배열 (로그인, 회원가입, 스웨거 등)
    private static final String[] AUTH_WHITELIST = {
            "/index.html",
            "/favicon.ico",
            "/assets/**", "/css/**", "/js/**", "/images/**",
            "/auth/login", "/auth/join", "/swagger-ui/**", "/api-docs", "swagger-ui-custom.html",
            "/ws/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 비활성화, CORS 기본값 적용
        http.csrf(AbstractHttpConfigurer::disable); // CSRF 방어 기능 비활성화
        // ㄴ> JWT 기반 Stateless API에서는 "폼 로그인-세션 기반" 인증이 아니므로 CSRF 방어 기능이 필요없다
        http.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()));

        // ㄴ> Spring Security의 CORS 설정을 활성화하되, 등록된 기본 CORS 설정(CorsConfigurationSource 빈)을 그대로 쓰겠다는 뜻
        // ㄴ> 교차 출처 리소스 공유(Cross-Origin Resource Sharing, CORS)는 브라우저가 자신의 출처가 아닌 다른 어떤
        // 출처(도메인, 스킴 혹은 포트)로부터 자원을 로딩하는 것을 허용하도록 서버가 허가해주는 HTTP 헤더 기반 메커니즘
        // ex) https://domain-a.com에서 제공되는 프론트엔트 JS 코드가 fetch()를 사용하여 https://domain-b.com/data.json에 요청하는 경우
        // 기존 웹 브라우저는 다른 도메인(origin)에 AJAX 요청을 보낼 때 보안 정책(Same-Origin Policy)때문에 차단한다
        // 이때, 서버가 "Access-Control-Allow-Origin", "Access-Control-Allow-Methods"같은 헤더를 내려주면, 브라우저가 요청을 허용한다.
        // ex) 프론트 : http://localhost:3000 & 백엔드 : http://localhost:8080
        // ㄴ> 서로 다른 origin이므로, CORS가 필요한 것이다.
        // 세밀한 설정을 위해서는 "CorsConfigurationSource" bean을 따로 등록해야한다

        // 2. 세션 관리 - 세션을 사용하지 않도록 설정한다. (JWT를 사용하기 위함임)
        http.sessionManagement(sessionManageMent -> sessionManageMent.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));

        // 3. 폼 로그인 & HTTP Basic 끄기
        // 폼 로그인(세션)과 Basic 인증을 꺼서 JWT만 사용하도록 만든다.
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 4. JWT 필터 등록 (나중에 추가)
        // UsernamePasswordAuthenticationFilter 앞에 직접 만든 JwtAuthFilter를 넣는다
        // 해당 필터로 JWT를 통한 인증 처리를 수행하게 한다.
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        // ㄴ> http.addFilterBefore("추가할 필터", 기존 필터)

        // 5. 예외 처리 핸들러 설정 - 인증 실패 및 접근 거부 예외를 처리하는 핸들러를 설정함
//        http.exceptionHandling(e -> e
//                .authenticationEntryPoint(authenticationEntryPoint) // authenticationEntryPoint(401) : 인증 자체가 없는 상태에서 보호된 자원에 접근할 때 사용하는 핸들러
//                // ㄴ> 내부적으로 commence 함수를 오버라이드 해놓았다.
//                .accessDeniedHandler(accessDeniedHandler)); // accessDeniedHandler(403) : 인중은 됐지만 권한이 부족한 경우 사용되는 핸들러
        // ㄴ> 내부적으로 handler 함수 정의해놓음


        // 6. 권한 규칙 작성
        // 1) 화이트 리스트에 있는 경로는 누구나 접근할 수 있도록 허용함
        // 2) 나머지 모든 경로는 @PreAuthorize 등의 메서드 수준 보안을 사용하여 접근을 제어함
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers(AUTH_WHITELIST).permitAll()
                    // 그 외 기본 정책
                    .anyRequest().authenticated();
        });
        // authorizeHttpRequests() : Spring Security에서 URL 요청 별 인가(Authorization, 권한 부여)규칙을 설정하는 DSL 함수
        // 파라미터 : Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
        // ㄴ> 의미 : 인가 규칙 빌터(AuthorizationManagerRequestMatcherRegistry)를 커스터마이징하라
        // .requestMatchers(...) : URL 패턴, HttpMethod, RequestMatcher 등을 지정할 수 있음


        return http.build();
    }

    // BCryptPasswordEncoder : Spring Security 프레임워크에서 제공하는 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스
    // BCrypt 해싱 함수를 사용해서 비밀번호를 인코딩할 수 있음
    // 패스워드 인코더도 Bean으로 등록하기 => Spring Security가 AuthenticationProvider -> UserDetailsService -> PasswordEncoder 과정을 처리해줄 때
    // 사용 할 수 있도록 하기 위함
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

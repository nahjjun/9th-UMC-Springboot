package com.example.umc_springboot.global.config;

import com.example.umc_springboot.global.exception.GlobalErrorCode;
import com.example.umc_springboot.global.response.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
    private final ObjectMapper objectMapper;

    // 인증 없이 접근 가능한 화이트 리스트 URL 모음 String 배열 (로그인, 회원가입, 스웨거 등)
    private static final String[] AUTH_WHITELIST = {
            // 정적 리소스
            "/index.html", "/favicon.ico",
            "/assets/**", "/css/**", "/js/**", "/images/**",

            // 인증/기타
            "/auth/login", "/auth/join", "/ws/**",

            // springdoc (Boot 3.x 기본)
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",

            // 인증 구현 전, 임시로 모든 경로 허용하기
            "/users/**",
//            "/reviews/**",
//            "/stores/**",
//            "/"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. cors 설정
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

        // 2. csrf 설정 off - swagger 테스트할 때만 끔
        http.csrf(AbstractHttpConfigurer::disable);


        // 3. 권한 규칙 작성
            // 1) 화이트 리스트에 있는 경로는 누구나 접근할 수 있도록 허용함
            // 2) 나머지 모든 경로는 @PreAuthorize 등의 메서드 수준 보안을 사용하여 접근을 제어함
        http.authorizeHttpRequests(authorize -> {
                authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll() // 화이트 리스트에 있는 url들은 모두 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole(): "ADMIN" 역할을 가진 사용자만 접근 가능하도록 제한함
                        .anyRequest().authenticated(); // 그 외 모든 요청에 대해 인증을 요구함
                })
                // http.formLogin(): FormLoginConfigurer를 설정하는 함수
                .formLogin(form -> form // HttpSecurity의 Form 로그인 설정
                                /**
                                 * defaultSuccessUrl: 로그인에 성공했을 시 이동할 url
                                 * alwaysUse:
                                 *      1. true일 때
                                 *          사용자가 처음에 '/myPage'를 가려다가 Login 페이지가 떴더라도, Login 후에는 원래 가려던
                                 *          경로를 무시하고 강제로 설정된 페이지로 보내는 설정
                                 *          2. false일 때(혹은 생략)
                                 *          사용자가 처음에 '/myPage'를 가려고 했을 때, Login 성공 후 다시 '/myPage'로 보내준다.
                                 *          (이 방식이 Spring UX 방식에 맞음)
                                 *   => 허나, 이 방식은 프론트/백엔드 방식에는 맞지 않다.
                                 *   => 이는 jsp같은 모놀리식 방식(서버가 화면을 그리는 경우)에 사용된다.
                                 */
//                        .defaultSuccessUrl("/swagger-ui/index.html", true) // defaulSuccessUrl, alwaysUse 속성 지정
                        .loginProcessingUrl("/auth/login") // 프론트가 로그인 요청을 보낼 주소
                        .successHandler((request, response, authentication) -> {
                            // 1. 로그인 성공 시 리다이렉트하지 않고, 200 상태 코드만 반환
                            response.setStatus(HttpServletResponse.SC_OK);

                            // 2. 응답 타입 설정 (JSON)
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            // 3. 반환할 데이터 생성
                            GlobalResponse<Void> globalResponse = GlobalResponse.success("200", "로그인 성공");

                            // 4. objectMapper로 자바 객체 JSON으로 직렬화
                            objectMapper.writeValue(response.getWriter(), globalResponse);
                                // -> response: HttpServletResponse 타입
                                // -> response.getWriter(): java.io.PrintWriter 타입을 반환함
                                // PrintWriter는 Http 응답 Body에 문자 기반으로 데이터를 쓰는 스트림이다.
                                // Tomcat(서블릿 컨테이너)의 HTTP 응답 버퍼에 데이터를 쓰는 스트림인 것임.
                                // -> globalResponse: 에러 정보를 담은 응답 통일 객체
                                // objectMapper의 writeValue():
                                // public void writeValue(Writer w, Object value) throws IOException
                                // value 객체의 필드를 reflection으로 읽어서 -> JSON 포맷 규칙에 따라 문자열로 변환
                                // -> 그 결과를 인자로 전달된 PrintWriter(response.getWriter())에 써 넣음
                        })
                        .failureHandler((request, response, exception) -> {
                            // 1. 로그인 실패 코드 설정
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            // 2. 응답 타입 설정 (JSON)
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            // 3. 반환할 데이터 생성
                            GlobalResponse<Void> globalResponse = GlobalResponse.success("403", "로그인 실패");

                            // 4. objectMapper로 자바 객체 JSON으로 직렬화
                            objectMapper.writeValue(response.getWriter(), globalResponse);
                        })
                        .permitAll()
                )
                // http.logout(): LogoutConfigurer를 설정하는 함수
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // 1. 로그아웃할 요청 url(프론트가 요청을 보낼 주소)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // 1. JSESSIONID 쿠키를 새로 생성 (이름은 같게)
                            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("JSESSIONID", null);

                            // 2. 설정 덮어쓰기 (핵심)
                            cookie.setPath("/");          // 쿠키가 생성된 경로와 똑같이 설정해야 지워짐 (매우 중요)
                            // -> Spring Boot(Tomcat)은 기본적으로 모든 페이지에서 세션을 유지해야하므로, JSESSIONID의 경로를 Path=/로 설정해서 발급한다.
                            // -> 따라서, 해당 쿠키도 동일한 경로로 새로 설정한다.
                            cookie.setMaxAge(0);          // 수명을 0으로 설정 -> 브라우저가 즉시 삭제함
                            // [중요] 생성 시 HttpOnly로 만들어졌으므로, 지울 때도 명시해주면 더 확실합니다.
                            // (Spring Boot 기본값은 HttpOnly=true 입니다)
                            cookie.setHttpOnly(true);
                            // 3. 응답에 추가
                            response.addCookie(cookie);

                            // 4. 로그아웃 성공 코드 설정
                            response.setStatus(HttpServletResponse.SC_OK);

                            // 5. 응답 타입 설정 (JSON)
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            // 6. 반환할 데이터 생성
                            GlobalResponse<Void> globalResponse = GlobalResponse.success("200", "로그아웃 성공");

                            // 7. objectMapper로 자바 객체 JSON으로 직렬화
                            objectMapper.writeValue(response.getWriter(), globalResponse);
                        })
                        .invalidateHttpSession(true) // 3. 세션 무효화 (필수임)
//                        .deleteCookies("JSESSIONID") // 4. 쿠키 삭제. 클라이언트가 갖고 있는 쿠기의 Key 이름 (필수임)
                        .permitAll()
                )
                // http.sessionManageMent(): SessionManagementConfigurer를 설정하는 함수
                // 인증, 인가 과정에서 세션을 어떻게 관리할 것인가?를 정의하는 함수
                // -> 주로 다루는 것:
                    // - 세션을 언제 만들지(sessionCreationPolicy)
                    // - 세션 고정 공격(Session Fixation)을 어떻게 막을지 (SessionFixation)
                    // - 한 계정으로 동시 접속을 어떻게 제한할지 (maximumSessions, maxSessionsPreventsLogin, expiredUrl)
                .sessionManagement(session -> session
                        // sessionCreationPolicy(): 세션 생성 전략(Session Creation Policy)을 설정하는 함수
                            // 1. SessionCreationPolicy.ALWAYS :
                                // - 요청이 들어올 때마다 세션이 없으면 무조건 새로 생성시키는 설정.
                                // - 어떤 요청이든 세션이 생길 수 있어, 불필요한 세션이 증가할 수 있음
                            // 2. SessionCreationPolicy.IF_REQUIRED :
                                // - 필요할 때만 세션 생성시키는 설정. (기본값) 로그인 성공해서 SecurityContext를 세션에 저장해야 할 때.
                                // - 일반적인 mvc 서비스에서 표준
                            // 3. SessionCreationPolicy.NEVER :
                                // - 직접 세션을 만들지는 않지만, 이미 존재하는 세션은 재사용하는 설정
                                // - 필터나 코드에서 request.getSession(false)로 세션을 만들지 않도록 신경 쓸 때 사용함
                                // - 외부에서 생성한 세션을 믿고 쓰는 특수 케이스 정도
                            // 4. SessionCreationPolicy.STATELESS :
                                // - 절대 세션을 만들지 않고, 기존 세션도 사용하지 않도록 하는 설정
                                // - JWT, OAuth2 Resource Server 같은 완전 무상태(Stateless) API 서버에서 사용
                                // - 세션 기반 로그인과는 반대로, 매 요청마다 토큰만 보고 인증함
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                        // 2. 세션 고정(Session Fixation) 공격 방지
                        // 다른 페이지에서 로그인 성공 시, 기존에 로그인된 세션을 파기하고 새로운 세션 ID를 발급한다.
                        // => 공격자가 미리 심어둔 세션 ID(JSESSIONID)로 정보를 탈취하는 것을 막는다.
                        .sessionFixation().changeSessionId()
                            // => .sessionFixation(): SessionFixationConfigurer를 반환하고,
                            // => .changeSessionId(): 현재 세션의 ID만 바꾼다. 세션에 저장된 기존 속성들은 유지하고, 세션 ID만 새로 발급한다.

                        // 3. 동시 접속 제어
                        .maximumSessions(1)     // 한 계정당 최대 1개 세션만 허용하도록 설정(중복 로그인이 방지)
                        // maxSessionsPreventsLogin(): maximumSessions()와 함께 동작하는 옵션. 최대 세션 수를 이미 채웠을 때, 새 로그인을 막을지 말지 설정
                        // true: 신규 로그인 차단 | false: 신규 로그인 허용(기존 세션 만료시킴)
                        .maxSessionsPreventsLogin(false)

                        // 4. 세션 만료 시 동작 설정 (REST API 방식)
                        // expiredUrl("/auth/logout") 대신 expiredSessionStrategy 사용
                        // expiredSessionStrategy 안에서 JSON 응답을 HttpServletResponse에 직접 작성한다.
                            // expiredSessionStrategy가 반환되면, ConcurrentSessionFilter 는 더 이상 필터 체인을 진행하지 않고 이 요청을 여기서 끝냅니다 (컨트롤러까지 절대 안 감).
                        .expiredSessionStrategy(event -> {
                            // => event.getResponse()는 HttpServletResponse를 반환함
                            HttpServletResponse response = event.getResponse();

                            // 1. 공통 응답 객체 생성
                            GlobalResponse<Void> globalResponse = GlobalResponse.error(
                                    GlobalErrorCode.SESSION_EXPIRED.getCode(),
                                    GlobalErrorCode.SESSION_EXPIRED.getMessage()
                            );

                            // 2. http 상태 코드 설정
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            // 3. 응답 타입 설정 (JSON)
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            // 4. ObjectMapper를 사용하여 GlobalResponse -> Json으로 직렬화(serialize)해서, 현재 HTTP 응답(response)의 Body에 그대로 써 넣는 코드
                            // objectMapper.writeValue(): 직렬화 함수
                            objectMapper.writeValue(response.getWriter(), globalResponse);
                                // -> response: HttpServletResponse 타입
                                // -> response.getWriter(): java.io.PrintWriter 타입을 반환함
                                    // PrintWriter는 Http 응답 Body에 문자 기반으로 데이터를 쓰는 스트림이다.
                                    // Tomcat(서블릿 컨테이너)의 HTTP 응답 버퍼에 데이터를 쓰는 스트림인 것임.
                                // -> globalResponse: 에러 정보를 담은 응답 통일 객체
                                // objectMapper의 writeValue(): 
                                    // public void writeValue(Writer w, Object value) throws IOException
                                    // value 객체의 필드를 reflection으로 읽어서 -> JSON 포맷 규칙에 따라 문자열로 변환
                                    // -> 그 결과를 인자로 전달된 PrintWriter(response.getWriter())에 써 넣음
                        })
                );
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

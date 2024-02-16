package com.gdscsmwu.earthus.plogus.config;

import com.gdscsmwu.earthus.plogus.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // Request
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                // 사용하지 않는 필터
                .formLogin()
                .disable()

                .oauth2Login(oauth2 ->
                // oauth2Login
                // - OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                        oauth2
                                .userInfoEndpoint( userInfoEndpointConfig ->
                                // userInfoEndpoint
                                // - OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                                        // 구글 로그인이 완료된 뒤의 후처리가 필요함.
                                        // 구글 로그인이 되면 코드를 받는 것이 아니라 엑세스 토큰 + 사용자 프로필 정보를 한 번에 받게 된다.
                                        // 때문에 AuthClient 관련 라이브러리가 굉장히 편리하다
                                        userInfoEndpointConfig
                                                .userService(principalOauth2UserService)
                                        // userService
                                        // - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                                        // - 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져옹 상태에서 추가로 진행하고자 하는 기능 명시
                                        // .userService()에 넣어줄 타입이 OAuth2UserService가 돼야 한다.
                                )
                        // 이를 PrincipalOauth2UserService.java에 만들어준다.
                        // 이렇게 구글 로그인을 통해 보낸 엑세스 토큰 + 사용자 프로필 정보들이
                        // PrincipalOauth2UserService.java의 loadUser 함수의 매개변수 userRequest에 리턴된다.

                                .defaultSuccessUrl("http://35.212.137.41:8080/")
                                //.failureUrl("/error")

                )

                .csrf()
                .disable()

                .headers()
                .disable()

                .httpBasic()
                .disable()

                .rememberMe()
                .disable()

                .logout()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();

    }

}

package com.gdscsmwu.earthus.plogus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // Request
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers(
//                        "/api/login", "/api/register"
//                ).permitAll()
//                .anyRequest().hasAnyRole("USER")
                .and()
                // 사용하지 않는 필터
                .formLogin()
                .disable()
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
//                .and()
        // 인증/인가 실패 Response
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint(objectMapper()))
//                .accessDeniedHandler(accessDeniedHandler(objectMapper()))
//                .and()
        // Redis Session
//                .addFilterBefore(userAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class)
        // CORS
//                .cors().configurationSource(corsConfigurationSource());

                /*
                .csrf(AbstractHttpConfigurer::disable)
                //.csrf().disable()
                .authorizeHttpRequests(
                        (authorizeRequests) ->
                                authorizeRequests
                                        .anyRequest().permitAll()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/join")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                );
                 */
                /*
                .logout((logout) ->
                        logout
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                //.logoutSuccessUrl("/")
                                // 로그아웃 성공 시 리다이렉트할 URL
                                .permitAll()
                );
                 */

        return httpSecurity.build();

    }

}

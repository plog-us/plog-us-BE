package com.gdscsmwu.earthus.plogus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Configuration
public class BCryptPasswordEncoderConfig extends DefaultOAuth2UserService {

    // password 암호화하는 Bean 등록
    // 해당 메서드의 리턴되는 오브젝트를 IoC(빈)로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}

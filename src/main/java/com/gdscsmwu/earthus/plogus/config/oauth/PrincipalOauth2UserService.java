package com.gdscsmwu.earthus.plogus.config.oauth;

import com.gdscsmwu.earthus.plogus.config.oauth.dto.OAuthAttributes;
import com.gdscsmwu.earthus.plogus.config.oauth.dto.SessionUsers;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    // SecurityConfig.java의 .userService()에 넣을 데이터 타입이 OAuth2UserService이므로
    // 해당 클래스 타입도 DefaultOAuth2UserService로 만들어준다.

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;
    private final HttpSession httpSession;



    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // loadUser 함수에 의해 후처리가 된다.
    @Override
    // OAuth 로그인했을 때 Override 한 이유
    // 1. PrincipalDetails 타입으로 묶기 위해
    // 2. OAuth 로그인 시 회원가입을 강제로 진행시키기 위해
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글 로그인을 통해 보낸 엑세스 토큰 + 사용자 프로필 정보들이 매개변수 userRequest에 리턴된다.

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registraionId = userRequest.getClientRegistration().getRegistrationId();
        String usernameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registraionId, usernameAttributeName, oAuth2User.getAttributes());

        // 회원가입을 강제로 진행해볼 예정
        String provider = userRequest.getClientRegistration().getClientId();
        // google
        String providerId = oAuth2User.getAttribute("sub");
        // OAuth 로그인이기 때문에 필요없지만 만들어준다.
        // 크게 의미가 없는 코드이다.
        String password = bCryptPasswordEncoder.encode(provider + "_" + providerId);

        Users users = saveOrUpdate(attributes, provider, providerId, password);
        httpSession.setAttribute("user", new SessionUsers(users));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(users.getRole().name())
                ),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );

    }

    private Users saveOrUpdate(OAuthAttributes attributes, String provider, String providerId, String password) {

        Users users = usersRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getUsername(), password, provider, providerId, attributes.getUserProfile()))
                .orElse(attributes.toEntity(password, provider, providerId));

        return usersRepository.save(users);

    }

}

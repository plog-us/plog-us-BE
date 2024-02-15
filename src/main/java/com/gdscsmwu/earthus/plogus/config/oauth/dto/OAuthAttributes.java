package com.gdscsmwu.earthus.plogus.config.oauth.dto;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.domain.UsersRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String password;
    private String email;
    private String provider;
    private String providerId;
    private String userProfile;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String username, String password, String email,
                           String provider, String providerId, String userProfile)
    {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.userProfile = userProfile;

    }

    // of()
    // - OAuth2User에서 반환하는 사용자 정보는 Map이기 대문에 값 하나하나를 변환해야만 한다.
    public static OAuthAttributes of(String registrationId, String userNameAttributionName, Map<String, Object> attributes) {

        return ofGoogle(userNameAttributionName, attributes);

    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .password((String) attributes.get("password"))
                .email((String) attributes.get("email"))
                .userProfile((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }



    // toEntity()
    // - User 엔티티를 생성한다.
    // - OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때이다.
    // - 가입할 때의 기본 권한을 GUEST로 주기 위해서 role 빌더값에는 Role.GUEST를 사용한다.
    // - OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성한다.
    public Users toEntity(String password, String provider, String providerId) {

        return Users.builder()
                .username(username)
                .password(password)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .userProfile(userProfile)
                .role(UsersRole.ROLE_USER)
                .build();

    }

}

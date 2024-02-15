package com.gdscsmwu.earthus.plogus.config.auth;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users users;
    // 콤포지션
    private Map<String, Object> attributes;



    // login 시 사용하는 생성자
    public PrincipalDetails(Users users) {
        this.users = users;
    }

    // OAuth 로그인 시 사용하는 생성자
    // OAuth2User로 로그인 할 때 Authentication에 PrincipalDetails를 저장할 것이다.
    // OAuth 로그인을 하면 attributes 정보와 user 정보를 가지게 될 것이다.
    // attributes 정보를 토대로 user 정보가 생성될 것이다.
    public PrincipalDetails(Users users, Map<String, Object> attributes) {
        this.users = users;
        this.attributes = attributes;
    }



    // 해당 user의 권한 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return users.getRole().name();
                    }
                }
        );

        return collection;
    }

    // 계정의 고유한 값을 PK로 넘겨준다
    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    // 계정이 만료되지 않았는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠기지 않았는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 비밀번호 변경이 1년이 지나지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되었는지
    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        //return attributes.get("sub");
        // 구글 회원 정보 PK가 sub이다.
        return null;
    }

}

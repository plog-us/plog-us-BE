package com.gdscsmwu.earthus.plogus.config.auth;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
//public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users users;

    // login 시 사용하는 생성자
    public PrincipalDetails(Users users) {
        this.users = users;
    }

    // 해당 user의 권한 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(() -> {
            return users.getRole().name();
        });

        return collection;
    }

    // 계정의 고유한 값을 PK로 넘겨준다
    @Override
    public String getUsername() {
        //return users.getEmail();
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

    //    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public String getName() {
//        //return attributes.get("sub");
//        // 구글 회원 정보 PK가 sub이다.
//        return null;
//    }

}

package com.gdscsmwu.earthus.plogus.config.oauth.dto;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Getter;

import java.io.Serializable;

// SessionUser에는 인증된 사용자 정보만 필요하다.
// 그 외에 필요한 정보들은 없으니 name, email, picture만 필드로 선언한다.

@Getter
public class SessionUsers implements Serializable {

    private String username;
    private String email;
    private String userProfile;

    public SessionUsers(Users users) {
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.userProfile = users.getUserProfile();
    }

}

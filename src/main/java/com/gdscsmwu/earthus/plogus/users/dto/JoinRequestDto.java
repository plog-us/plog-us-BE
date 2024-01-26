package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.domain.UsersRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank(message = "닉네임")
    private String username;

    @NotBlank(message = "비밀번호")
    private String password;

    @NotBlank(message = "이메일")
    private String email;

    public Users toEntity(String encodedPassword) {
        return Users.builder()
                .username(this.username)
                .password(encodedPassword)
                .email(this.email)
                .role(UsersRole.ROLE_USER)
                //.provider(null)
                //.providerId(null)
                .build();
    }

}

package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.domain.UsersRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginResponseDto {

    private Long userUuid;
    private String username;
    private String password;
    private String email;
    private UsersRole role;
    private String provider;
    private String providerId;
    private LocalDateTime createDate;
    private String userProfile;
    private String profileUuid;

    public LoginResponseDto(Users entity) {
        this.userUuid = entity.getUserUuid();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.provider = entity.getProvider();
        this.providerId = entity.getProviderId();
        this.createDate = entity.getCreateDate();
        this.userProfile = entity.getUserProfile();
        this.profileUuid = entity.getProfileUuid();
    }

}

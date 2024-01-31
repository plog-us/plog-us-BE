package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Getter;

// 메인페이지 : username 조회
@Getter
public class UsernameResponseDto {

    private String username;

    public UsernameResponseDto(Users entity) {
        this.username = entity.getUsername();
    }

}

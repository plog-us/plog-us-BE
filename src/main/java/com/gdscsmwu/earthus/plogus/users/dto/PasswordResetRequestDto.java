package com.gdscsmwu.earthus.plogus.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordResetRequestDto {

    private String email;
    private String newPassword;

}

package com.gdscsmwu.earthus.plogus.users.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

// 프로필 사진 수정 : userProfile, profileUuid
@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class UserProfileUpdateRequestDto {

    private MultipartFile userProfile;

//    @Builder
//    public UserProfileUpdateRequestDto(MultipartFile userProfile) {
//        this.userProfile = userProfile;
//    }

}

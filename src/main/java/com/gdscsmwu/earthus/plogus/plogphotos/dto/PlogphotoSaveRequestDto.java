package com.gdscsmwu.earthus.plogus.plogphotos.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.plogphotos.domain.Plogphotos;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

// 플로깅 사진 업로드 : ploggingUuid, plogphotoUrl, photoUuid
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlogphotoSaveRequestDto {

    private Long ploggingUuid;
    private String plogphotoUrl;
    private String photoUuid;
    private MultipartFile plogphoto;



//    @Builder
//    public PlogphotoSaveRequestDto(Long ploggingUuid, String plogphotoUrl, String photoUuid) {
//        this.ploggingUuid = ploggingUuid;
//        this.plogphotoUrl = plogphotoUrl;
//        this.photoUuid = photoUuid;
//    }

    public Plogphotos toEntity(Plogging plogging, String plogphotoUrl, String photoUuid) {

        return Plogphotos.builder()
                .plogging(plogging)
                .plogphotoUrl(plogphotoUrl)
                .photoUuid(photoUuid)
                .build();

    }

}

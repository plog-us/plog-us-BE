package com.gdscsmwu.earthus.plogus.plogphotos.controller;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.plogging.repository.PloggingRepository;
import com.gdscsmwu.earthus.plogus.plogphotos.dto.PlogphotoSaveRequestDto;
import com.gdscsmwu.earthus.plogus.plogphotos.service.PlogphotosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PlogphotosController {

    private final PlogphotosService plogphotosService;
    private final PloggingRepository ploggingRepository;

    // 플로깅 사진 업로드 : ploggingUuid, plogphotoUrl, photoUuid
    @PostMapping("/uploadplogphoto/{ploggingUuid}")
    public Long uploadPlogphoto(@PathVariable Long ploggingUuid, PlogphotoSaveRequestDto plogphotoSaveRequestDto) throws IOException {

        Plogging plogging = ploggingRepository.getReferenceById(ploggingUuid);

        return plogphotosService.uploadPlogphoto(plogging, plogphotoSaveRequestDto);

    }

}

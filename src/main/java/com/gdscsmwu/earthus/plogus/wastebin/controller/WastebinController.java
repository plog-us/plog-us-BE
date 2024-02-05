package com.gdscsmwu.earthus.plogus.wastebin.controller;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.wastebin.dto.WastebinResponseDto;
import com.gdscsmwu.earthus.plogus.wastebin.service.WastebinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WastebinController {

    private final WastebinService wastebinService;

    // wastebin 조회 : binUuid, plogUuid, binAddress, binLatitude, binLongitude
    @GetMapping("/wastebin/{plogUuid}")
    public List<WastebinResponseDto> viewWastebin(@PathVariable Ploglocation plogUuid) {

        return wastebinService.viewWastebin(plogUuid);

    }

}

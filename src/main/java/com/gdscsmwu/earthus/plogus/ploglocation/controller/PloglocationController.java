package com.gdscsmwu.earthus.plogus.ploglocation.controller;

import com.gdscsmwu.earthus.plogus.ploglocation.dto.PloglocationResponseDto;
import com.gdscsmwu.earthus.plogus.ploglocation.service.PloglocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PloglocationController {

    private final PloglocationService ploglocationService;

    // ploglocation 전체 조회 : plogUuid, plogAddress, plogLatitude, plogLongitude, plogCount
    @GetMapping("/ploglocation")
    public List<PloglocationResponseDto> viewploglocation() {

        return ploglocationService.viewPloglocation();

    }

}

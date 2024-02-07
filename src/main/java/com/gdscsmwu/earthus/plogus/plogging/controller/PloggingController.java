package com.gdscsmwu.earthus.plogus.plogging.controller;

import com.gdscsmwu.earthus.plogus.plogging.dto.*;
import com.gdscsmwu.earthus.plogus.plogging.service.PloggingService;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PloggingController {

    private final PloggingService ploggingService;

    // 플로깅 기록 세부 조회
    @GetMapping("/plogginglog/details/{ploggingUuid}")
    public PloggingResponseDto findById(@PathVariable Long ploggingUuid) {

        return ploggingService.findById(ploggingUuid);

    }

    // 플로깅 기록 리스트 조회
    @GetMapping("/plogginglog/list/{userUuid}")
    public List<PloggingListResponseDto> getPloggingList(@PathVariable Users userUuid) {

        return ploggingService.getPloggingList(userUuid);

    }

    // 플로깅 시작 : userUuid, plogUuid, ploggingStart
//    @PostMapping("/startplogging/{plogUuid}")
//    public Long ploggingStart(@PathVariable Long plogUuid, @RequestBody PloggingStartRequestDto requestDto) {
//
//        return ploggingService.ploggingStart(requestDto.getUserUuid(), plogUuid);
//
//    }
    @PostMapping("/startplogging/{userUuid}/{plogUuid}")
    public Long ploggingStart(@PathVariable Long userUuid, @PathVariable Long plogUuid) {

        return ploggingService.ploggingStart(userUuid, plogUuid);

    }

    // 플로깅 중 : ploggingScore + 1
    @PutMapping("/scoreplogging/{ploggingUuid}")
    public Long ploggingScore(@PathVariable Long ploggingUuid) {

        ploggingService.ploggingScore(ploggingUuid);

        return ploggingUuid;

    }

    // 플로깅 종료 : ploggingEnd, ploggingTime
    // 플로깅 장소의 플로깅 횟수 + 1 : plogCount + 1
    @PutMapping("/finishplogging/{ploggingUuid}")
    public PloggingFinishResponseDto ploggingFinish(@PathVariable Long ploggingUuid, @RequestBody PloggingFinishRequestDto requestDto) {

        return ploggingService.ploggingFinish(ploggingUuid, requestDto);

    }

}

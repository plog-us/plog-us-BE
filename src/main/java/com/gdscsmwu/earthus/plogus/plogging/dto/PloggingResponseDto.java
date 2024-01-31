package com.gdscsmwu.earthus.plogus.plogging.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.plogphotos.domain.Plogphotos;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 플로깅 기록 세부 조회
@Getter
public class PloggingResponseDto {

    private Long userUuid;
    private Long ploggingUuid;
    private LocalDateTime ploggingStart;
    private Time ploggingTime;
    private BigDecimal ploggingDistance;
    private int ploggingScore;
    private List<String> plogphotoUrl;

    public PloggingResponseDto(Plogging entity) {
        this.userUuid = entity.getUsers().getUserUuid();
        this.ploggingUuid = entity.getPloggingUuid();
        this.ploggingStart = entity.getPloggingStart();
        this.ploggingTime = entity.getPloggingTime();
        this.ploggingDistance = entity.getPloggingDistance();
        this.ploggingScore = entity.getPloggingScore();
        //if(entity.getPlogphotos() != null) {
            //this.plogphotos = entity.getPlogphotos();
        this.plogphotoUrl = entity.getPlogphotos().stream()
                .map(Plogphotos::getPlogphotoUrl)
                .collect(Collectors.toList());
        //}
    }
}

package com.gdscsmwu.earthus.plogus.plogging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

// 플로깅 종료
@Getter
@NoArgsConstructor
public class PloggingFinishRequestDto {

    private LocalDateTime ploggingEnd;
    private Time ploggingTime;
    private BigDecimal ploggingDistance;
    //private int ploggingScore;

    @Builder
    public PloggingFinishRequestDto(LocalDateTime ploggingEnd, Time ploggingTime, BigDecimal ploggingDistance) {
        this.ploggingEnd = ploggingEnd;
        this.ploggingTime = ploggingTime;
        this.ploggingDistance = ploggingDistance;
        //this.ploggingScore = ploggingScore;
    }

}

package com.gdscsmwu.earthus.plogus.plogging.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import lombok.Getter;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

// 플로깅 기록 리스트 조회
@Getter
public class PloggingListResponseDto {

    private Long userUuid;
    private Long ploggingUuid;
    private LocalDateTime ploggingStart;
    //private Duration ploggingTime;
    private Time ploggingTime;
    private int ploggingScore;

    public PloggingListResponseDto (Plogging entity) {
        this.userUuid = entity.getUsers().getUserUuid();
        this.ploggingUuid = entity.getPloggingUuid();
        this.ploggingStart = entity.getPloggingStart();
        this.ploggingTime = entity.getPloggingTime();
        this.ploggingScore = entity.getPloggingScore();
    }

}

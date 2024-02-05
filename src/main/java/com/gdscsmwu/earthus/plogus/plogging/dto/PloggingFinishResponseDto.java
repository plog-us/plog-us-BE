package com.gdscsmwu.earthus.plogus.plogging.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;

// 플로깅 종료 view : ploggingEnd, ploggingTime
@Getter
public class PloggingFinishResponseDto {

    private Long userUuid;
    private Long plogUuid;
    // 플로깅 장소의 플로깅 횟수 + 1 : plogCount + 1
    private int plogCount;
    private String plogAddress;
    private Time ploggingTime;
    private BigDecimal ploggingDistance;
    private int ploggingScore;

    public PloggingFinishResponseDto(Plogging entity) {
        this.userUuid = entity.getUsers().getUserUuid();
        this.plogUuid = entity.getPloglocation().getPlogUuid();
        // 플로깅 장소의 플로깅 횟수 + 1 : plogCount + 1
        this.plogCount = entity.getPloglocation().getPlogCount();
        this.plogAddress = entity.getPloglocation().getPlogAddress();
        this.ploggingTime = entity.getPloggingTime();
        this.ploggingDistance = entity.getPloggingDistance();
        this.ploggingScore = entity.getPloggingScore();
    }

}

package com.gdscsmwu.earthus.plogus.plogging.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 플로깅 시작
@Getter
@NoArgsConstructor
public class PloggingStartRequestDto {

    //private Long ploggingUuid;
    private Long userUuid;
    private Long plogUuid;
//    private LocalDateTime ploggingStart;
//    private BigDecimal ploggingDistance;
//    private int ploggingScore;

    @Builder
    //public PloggingStartRequestDto(Long ploggingUuid, Long userUuid, Long plogUuid, LocalDateTime ploggingStart, BigDecimal ploggingDistance, int ploggingScore) {
    public PloggingStartRequestDto(Long userUuid, Long plogUuid, LocalDateTime ploggingStart, BigDecimal ploggingDistance, int ploggingScore) {
        //this.ploggingUuid = ploggingUuid;
        this.userUuid = userUuid;
        this.plogUuid = plogUuid;
//        this.ploggingStart = ploggingStart;
//        this.ploggingDistance = ploggingDistance;
//        this.ploggingScore = ploggingScore;
    }

    public Plogging toEntity(Users users, Ploglocation ploglocation) {
        return Plogging.builder()
                .users(users)
                .ploglocation(ploglocation)
//                .ploggingStart(ploggingStart)
//                .ploggingDistance(ploggingDistance)
//                .ploggingScore(ploggingScore)
                .build();
    }

}

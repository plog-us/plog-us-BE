package com.gdscsmwu.earthus.plogus.ploglocation.dto;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import lombok.Getter;

import java.math.BigDecimal;

// ploglocation 전체 조회 : plogUuid, plogAddress, plogLatitude, plogLongitude, plogCount
@Getter
public class PloglocationResponseDto {

    private Long plogUuid;
    private String plogAddress;
    private BigDecimal plogLatitude;
    private BigDecimal plogLongitude;
    private int plogCount;

    public PloglocationResponseDto(Ploglocation entity) {
        this.plogUuid = entity.getPlogUuid();
        this.plogAddress = entity.getPlogAddress();
        this.plogLatitude = entity.getPlogLatitude();
        this.plogLongitude = entity.getPlogLongitude();
        this.plogCount = entity.getPlogCount();
    }

}

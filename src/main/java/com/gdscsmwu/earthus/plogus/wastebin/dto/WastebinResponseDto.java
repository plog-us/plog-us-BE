package com.gdscsmwu.earthus.plogus.wastebin.dto;

import com.gdscsmwu.earthus.plogus.wastebin.domain.Wastebin;
import lombok.Getter;

import java.math.BigDecimal;

// wastebin 조회 : binUuid, plogUuid, binAddress, binLatitude, binLongitude
@Getter
public class WastebinResponseDto {

    private Long binUuid;
    private Long plogUuid;
    private String binAddress;
    private BigDecimal binLatitude;
    private BigDecimal binLongutude;

    public WastebinResponseDto(Wastebin entity) {
        this.binUuid = entity.getBinUuid();
        this.plogUuid = entity.getPloglocation().getPlogUuid();
        this.binAddress = entity.getBinAddress();
        this.binLatitude = entity.getBinLatitude();
        this.binLongutude = entity.getBinLongitude();
    }

}

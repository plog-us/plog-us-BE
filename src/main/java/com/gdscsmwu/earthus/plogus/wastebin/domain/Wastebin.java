package com.gdscsmwu.earthus.plogus.wastebin.domain;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "wastebin")
public class Wastebin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bin_uuid", columnDefinition = "bigint", nullable = false)
    private Long binUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plog_uuid", columnDefinition = "bigint", nullable = false)
    private Ploglocation ploglocation;

    @Column(name = "bin_address", columnDefinition = "varchar(255)", nullable = false)
    private String binAddress;

    @Column(name = "bin_latitude", columnDefinition = "decimal(9, 7)")
    private BigDecimal binLatitude;

    @Column(name = "bin_longitude", columnDefinition = "decimal(10, 7)")
    private BigDecimal binLongitude;



    @Builder
    public Wastebin (Ploglocation ploglocation, String binAddress, BigDecimal binLatitude, BigDecimal binLongitude) {
        this.ploglocation = ploglocation;
        this.binAddress = binAddress;
        this.binLatitude = binLatitude;
        this.binLongitude = binLongitude;
    }

}

package com.gdscsmwu.earthus.plogus.ploglocation.domain;

import com.gdscsmwu.earthus.plogus.wastebin.domain.Wastebin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ploglocation")
public class Ploglocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plog_uuid", columnDefinition = "bigint", nullable = false)
    private Long plogUuid;

    @Column(name = "plog_address", columnDefinition = "varchar(255)", nullable = false)
    private String plogAddress;

    @Column(name = "plog_latitude", columnDefinition = "decimal(9, 7)")
    private BigDecimal plogLatitude;

    @Column(name = "plog_longitude", columnDefinition = "decimal(10, 7)")
    private BigDecimal plogLongitude;

    @Column(name = "plog_count", columnDefinition = "int")
    private int plogCount;



    @OneToMany(mappedBy = "ploglocation")
    private List<Wastebin> wastebin = new ArrayList<Wastebin>();



    @Builder
    public Ploglocation (String plogAddress, BigDecimal plogLatitude, BigDecimal plogLongitude, int plogCount, List<Wastebin> wastebin) {
        this.plogAddress = plogAddress;
        this.plogLatitude = plogLatitude;
        this.plogLongitude = plogLongitude;
        this.plogCount = plogCount;
        this.wastebin = wastebin;
    }

}

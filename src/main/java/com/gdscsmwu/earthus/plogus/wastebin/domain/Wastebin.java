package com.gdscsmwu.earthus.plogus.wastebin.domain;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

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

    @Column(name = "bin_point", columnDefinition = "point", nullable = false)
    private Point binPoint;

    @Builder
    public Wastebin (Ploglocation ploglocation, String binAddress, Point binPoint) {
        this.ploglocation = ploglocation;
        this.binAddress = binAddress;
        this.binPoint = binPoint;
    }

}

package com.gdscsmwu.earthus.plogus.ploglocation.domain;

import com.gdscsmwu.earthus.plogus.wastebin.domain.Wastebin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
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

    @Column(name = "plog_point", columnDefinition = "point")
    private Point plogPoint;



    @OneToMany(mappedBy = "ploglocation")
    private List<Wastebin> wastebin = new ArrayList<Wastebin>();



    @Builder
    public Ploglocation (String plogAddress, Point plogPoint, List<Wastebin> wastebin) {
        this.plogAddress = plogAddress;
        this.plogPoint = plogPoint;
        this.wastebin = wastebin;
    }

}

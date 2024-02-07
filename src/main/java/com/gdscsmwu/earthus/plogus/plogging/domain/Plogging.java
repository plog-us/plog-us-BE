package com.gdscsmwu.earthus.plogus.plogging.domain;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.plogphotos.domain.Plogphotos;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
//@Getter
@Data
@NoArgsConstructor
@Entity
@Table(name = "plogging")
public class Plogging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_uuid", columnDefinition = "bigint", nullable = false)
    private Long ploggingUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", columnDefinition = "bigint", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plog_uuid", columnDefinition = "bigint", nullable = false)
    private Ploglocation ploglocation;

    @CreationTimestamp
    @Column(name = "plogging_start", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime ploggingStart;

    @CreationTimestamp
    @Column(name = "plogging_end", columnDefinition = "timestamp")
    private LocalDateTime ploggingEnd;

    @Column(name = "plogging_time", columnDefinition = "time")
    @ColumnDefault("00:00:00")
    // @Formula 어노테이션을 사용하여 시간 간격을 계산하는 쿼리를 정의합니다.
    //@Formula("(plogging_end - plogging_start")
    //private Duration ploggingTime;
    private Time ploggingTime;

    @Column(name = "plogging_distance", columnDefinition = "decimal(2, 1)")
    @ColumnDefault("0.0")
    private BigDecimal ploggingDistance;

    @Column(name = "plogging_score", columnDefinition = "int")
    @ColumnDefault("0")
    private int ploggingScore;



    @OneToMany(mappedBy = "plogging")
    private List<Plogphotos> plogphotos = new ArrayList<Plogphotos>();



    @Builder
    //public Plogging (Users users, Ploglocation ploglocation, LocalDateTime ploggingStart, LocalDateTime ploggingEnd, Duration ploggingTime, BigDecimal ploggingDistance, int ploggingScore, List<Plogphotos> plogphotos) {
    public Plogging (Users users, Ploglocation ploglocation, LocalDateTime ploggingStart, LocalDateTime ploggingEnd, Time ploggingTime, BigDecimal ploggingDistance, int ploggingScore, List<Plogphotos> plogphotos) {
        this.users = users;
        this.ploglocation = ploglocation;
        this.ploggingStart = ploggingStart;
        this.ploggingEnd = ploggingEnd;
        this.ploggingTime = ploggingTime;
        this.ploggingDistance = ploggingDistance;
        this.ploggingScore = ploggingScore;
        this.plogphotos = plogphotos;
    }

    // 플로깅 종료 update : ploggingEnd, ploggingTime
    public void ploggingFinish(LocalDateTime ploggingEnd, Time ploggingTime, BigDecimal ploggingDistance) {
        this.ploggingEnd = ploggingEnd;
        this.ploggingTime = ploggingTime;
        this.ploggingDistance = ploggingDistance;
    }

}

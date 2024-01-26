package com.gdscsmwu.earthus.plogus.plogphotos.domain;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "plogphotos")
public class Plogphotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogphoto_uuid", columnDefinition = "bigint", nullable = false)
    private Long plogphotoUuid;

    // plogging과 양방향 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_uuid", columnDefinition = "bigint", nullable = false)
    private Plogging plogging;

    @Column(name = "plogphoto_url", columnDefinition = "text", nullable = false)
    private String plogphotoUrl;

    @Column(name = "plogphoto_created", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime plogphotoCreated;

    @Builder
    public Plogphotos (Plogging plogging, String plogphotoUrl, LocalDateTime plogphotoCreated) {
        this.plogging = plogging;
        this.plogphotoUrl = plogphotoUrl;
        this.plogphotoCreated = plogphotoCreated;
    }

}

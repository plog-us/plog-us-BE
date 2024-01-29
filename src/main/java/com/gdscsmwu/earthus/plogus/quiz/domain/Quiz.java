package com.gdscsmwu.earthus.plogus.quiz.domain;

import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_uuid", columnDefinition = "bigint", nullable = false)
    private Long quizUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", columnDefinition = "bigint", nullable = false)
    private Users users;

    @Column(name = "quiz_score", columnDefinition = "int", nullable = false)
    private int quizScore;



    @Builder
    public Quiz (Users users, int quizScore) {
        this.users = users;
        this.quizScore = quizScore;
    }

}

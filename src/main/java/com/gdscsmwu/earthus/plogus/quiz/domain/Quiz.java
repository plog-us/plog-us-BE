package com.gdscsmwu.earthus.plogus.quiz.domain;

import com.gdscsmwu.earthus.plogus.question.domain.Question;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_uuid", columnDefinition = "bigint", nullable = false)
    private Question question;

    @Column(name = "quiz_score", columnDefinition = "int", nullable = false)
    private int quizScore;

    @Column(name = "quiz_created", columnDefinition = "timestamp", nullable = false)
    private LocalDate quizCreated;



    @Builder
    public Quiz (Users users, Question question, int quizScore, LocalDate quizCreated) {
        this.users = users;
        this.question = question;
        this.quizScore = quizScore;
        this.quizCreated = quizCreated;
    }

}

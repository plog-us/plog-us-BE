package com.gdscsmwu.earthus.plogus.question.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_uuid", columnDefinition = "bigint", nullable = false)
    private Long questionUuid;

    @Column(name = "question_context", columnDefinition = "text", nullable = false)
    private String questionContext;

    @Column(name = "question_correct", columnDefinition = "varchar(255)", nullable = false)
    private String questionCorrect;

    @Column(name = "question_incorrect", columnDefinition = "varchar(255)", nullable = false)
    private String questionIncorrect;

    @Builder Question(String questionContext, String questionCorrect, String questionIncorrect) {
        this.questionContext = questionContext;
        this.questionCorrect = questionCorrect;
        this.questionIncorrect = questionIncorrect;
    }

}

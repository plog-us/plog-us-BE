package com.gdscsmwu.earthus.plogus.question.dto;

import com.gdscsmwu.earthus.plogus.question.domain.Question;
import lombok.Getter;

// 당일 일일퀴즈 안했을 경우 questionDto 조회, 했을 경우 null 반환 : questionUuid, questionContext, questionCorrect, questionIncorrect
@Getter
public class QuestionDto {

    private Long questionUuid;
    private String questionContext;
    private String questionCorrect;
    private String questionIncorrect;

    public QuestionDto(Question entity) {
        questionUuid = entity.getQuestionUuid();
        questionContext = entity.getQuestionContext();
        questionCorrect = entity.getQuestionCorrect();
        questionIncorrect = entity.getQuestionIncorrect();
    }

}

package com.gdscsmwu.earthus.plogus.quiz.controller;

import com.gdscsmwu.earthus.plogus.question.dto.QuestionDto;
import com.gdscsmwu.earthus.plogus.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    // 당일 일일퀴즈 안했을 경우 questionDto 조회, 했을 경우 null 반환 : questionUuid, questionContext, questionCorrect, questionIncorrect
    @GetMapping("/quiz/{userUuid}")
    public QuestionDto getQuiz(@PathVariable Long userUuid) {

        return quizService.getQuiz(userUuid);

    }

    // 퀴즈 정답 : quizUuid, userUuid, questionUuid, quizScore = 1, quizCreated
    @PostMapping("/quiz/correct/{userUuid}/{questionUuid}")
    public Long quizCorrect(@PathVariable Long userUuid, @PathVariable Long questionUuid) {

        return quizService.quizCorrect(userUuid, questionUuid);

    }

    // 퀴즈 오답 : quizUuid, userUuid, questionUuid, quizScore = 0, quizCreated
    @PostMapping("/quiz/incorrect/{userUuid}/{questionUuid}")
    public Long quizIncorrect(@PathVariable Long userUuid, @PathVariable Long questionUuid) {

        return quizService.quizIncorrect(userUuid, questionUuid);

    }

}

package com.gdscsmwu.earthus.plogus.quiz.controller;

import com.gdscsmwu.earthus.plogus.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    // 퀴즈 정답 : quizScore = 1
    @PostMapping("/quiz/correct/{userUuid}")
    public Long quizCorrect(@PathVariable Long userUuid) {

        return quizService.quizCorrect(userUuid);

    }

}

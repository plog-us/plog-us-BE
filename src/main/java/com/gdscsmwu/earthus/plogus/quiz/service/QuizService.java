package com.gdscsmwu.earthus.plogus.quiz.service;

import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import com.gdscsmwu.earthus.plogus.quiz.repository.QuizRepository;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UsersRepository usersRepository;

    // 퀴즈 정답 : quizScore = 1
    @Transactional
    public Long quizCorrect(Long userUuid) {

        Users users = usersRepository.findByUserUuid(userUuid);

        Quiz quiz = Quiz.builder()
                .users(users)
                .quizScore(1)
                .quizCreated(LocalDateTime.now())
                .build();

        return quizRepository.save(quiz).getQuizUuid();

    }

}

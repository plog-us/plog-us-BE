package com.gdscsmwu.earthus.plogus.quiz.repository;

import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // 당일 일일퀴즈 안했을 경우 questionDto 조회, 했을 경우 null 반환 : questionUuid, questionContext, questionCorrect, questionIncorrect
    Optional<Quiz> findByUsersAndQuizCreated(Users users, LocalDate today);

}

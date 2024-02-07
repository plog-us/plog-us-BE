package com.gdscsmwu.earthus.plogus.question.repository;

import com.gdscsmwu.earthus.plogus.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 당일 일일퀴즈 안했을 경우 questionDto 조회, 했을 경우 null 반환 : questionUuid, questionContext, questionCorrect, questionIncorrect
    @Query(value = "SELECT * FROM question ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();

    // 퀴즈 정답 : quizUuid, userUuid, questionUuid, quizScore = 1, quizCreated
    // 퀴즈 오답 : quizUuid, userUuid, questionUuid, quizScore = 0, quizCreated
    Question findByQuestionUuid(Long questionUuid);

}

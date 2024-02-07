package com.gdscsmwu.earthus.plogus.quiz.service;

import com.gdscsmwu.earthus.plogus.question.domain.Question;
import com.gdscsmwu.earthus.plogus.question.dto.QuestionDto;
import com.gdscsmwu.earthus.plogus.question.repository.QuestionRepository;
import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import com.gdscsmwu.earthus.plogus.quiz.repository.QuizRepository;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UsersRepository usersRepository;

    // 당일 일일퀴즈 안했을 경우 questionDto 조회, 했을 경우 null 반환 : questionUuid, questionContext, questionCorrect, questionIncorrect
    @Transactional
    public QuestionDto getQuiz(Long userUuid) {

        Users users = usersRepository.findByUserUuid(userUuid);
        Optional<Quiz> quiz = quizRepository.findByUsersAndQuizCreated(users, LocalDate.now());

        if (quiz.isEmpty()) {

            //Question question = questionRepository.findRandomQuestionUuid();
            Question question = questionRepository.findRandomQuestion();

            return new QuestionDto(question);

        }
        else {

            return null;

        }

    }

    // 퀴즈 정답 : quizUuid, userUuid, questionUuid, quizScore = 1, quizCreated
    @Transactional
    public Long quizCorrect(Long userUuid, Long questionUuid) {

        Users users = usersRepository.findByUserUuid(userUuid);
        Question question = questionRepository.findByQuestionUuid(questionUuid);

        Quiz quiz = Quiz.builder()
                .users(users)
                .question(question)
                .quizScore(1)
                .quizCreated(LocalDate.now())
                .build();

        return quizRepository.save(quiz).getQuizUuid();

    }

    // 퀴즈 오답 : quizUuid, userUuid, questionUuid, quizScore = 0, quizCreated
    @Transactional
    public Long quizIncorrect(Long userUuid, Long questionUuid) {

        Users users = usersRepository.findByUserUuid(userUuid);
        Question question = questionRepository.findByQuestionUuid(questionUuid);

        Quiz quiz = Quiz.builder()
                .users(users)
                .question(question)
                .quizScore(0)
                .quizCreated(LocalDate.now())
                .build();

        return quizRepository.save(quiz).getQuizUuid();

    }

}

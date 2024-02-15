package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Getter;

// 퀴즈 리더보드 : username, totalQuizScore
@Getter
public class LeaderboardQuizResponseDto {

    private String username;
    private int totalQuizScore;

    public LeaderboardQuizResponseDto(Users entity) {

        this.username = entity.getUsername();

        if (entity.getQuiz() != null) {

            this.totalQuizScore = entity.getQuiz().stream()
                    .mapToInt(Quiz::getQuizScore)
                    .sum();

        }

    }

}

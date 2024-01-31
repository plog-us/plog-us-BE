package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 마이페이지 : username, userprofile, ploggingStart, totalPloggingScore, totalQuizScore 조회
@Getter
public class MypageResponseDto {

    private Long userUuid;
    private String username;
    private String userProfile;
    private List<LocalDateTime> ploggingStart;
    private int totalPloggingScore;
    private int totalQuizScore;

    public MypageResponseDto(Users entity) {
        this.userUuid = entity.getUserUuid();
        this.username = entity.getUsername();
        this.userProfile = entity.getUserProfile();
        if (entity.getPlogging() != null) {
            this.ploggingStart = entity.getPlogging().stream()
                    .map(Plogging::getPloggingStart)
                    .collect(Collectors.toList());
            this.totalPloggingScore = entity.getPlogging().stream()
                    .mapToInt(Plogging::getPloggingScore)
                    .sum();
        }
        if (entity.getQuiz() != null) {
            this.totalQuizScore = entity.getQuiz().stream()
                    .mapToInt(Quiz::getQuizScore)
                    .sum();
        }
    }

}

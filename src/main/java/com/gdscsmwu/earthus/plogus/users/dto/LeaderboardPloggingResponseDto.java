package com.gdscsmwu.earthus.plogus.users.dto;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import lombok.Getter;

// 플로깅 리더보드 : username, totalPloggingScore
@Getter
public class LeaderboardPloggingResponseDto {

    private String username;
    private int totalPloggingScore;

    public LeaderboardPloggingResponseDto(Users entity) {
        this.username = entity.getUsername();
        if (entity.getPlogging() != null) {
            this.totalPloggingScore = entity.getPlogging().stream()
                    .mapToInt(Plogging::getPloggingScore)
                    .sum();
        }
    }

}

package com.gdscsmwu.earthus.plogus.plogging.service;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.plogging.dto.*;
import com.gdscsmwu.earthus.plogus.plogging.repository.PloggingRepository;
import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.ploglocation.repository.PloglocationRepository;
import com.gdscsmwu.earthus.plogus.users.domain.Users;
import com.gdscsmwu.earthus.plogus.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final UsersRepository usersRepository;
    private final PloglocationRepository ploglocationRepository;

    // 플로깅 기록 세부 조회
    @Transactional
    public PloggingResponseDto findById(Long ploggingUuid) {

        Plogging entity = ploggingRepository.findById(ploggingUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅이 없습니다. ploggingUuid = " + ploggingUuid));

        return new PloggingResponseDto(entity);

    }

    // 플로깅 기록 리스트 조회
    @Transactional(readOnly = true)
    public List<PloggingListResponseDto> getPloggingList(Users userUuid) {

        return ploggingRepository.findPloggingsByUsers(userUuid).stream()
                .map(PloggingListResponseDto::new)
                .collect(Collectors.toList());

    }

    // 플로깅 시작 : userUuid, plogUuid, ploggingStart
    @Transactional
    public Long ploggingStart(Long userUuid, Long plogUuid) {

        Users users = usersRepository.findByUserUuid(userUuid);
        Ploglocation ploglocation = ploglocationRepository.findByPlogUuid(plogUuid);

        PloggingStartRequestDto ploggingStartRequestDto = PloggingStartRequestDto.builder()
                .userUuid(userUuid)
                .plogUuid(plogUuid)
                .build();

        Plogging plogging = ploggingStartRequestDto.toEntity(users, ploglocation);

        return ploggingRepository.save(plogging).getPloggingUuid();

    }

    // 플로깅 종료 : ploggingEnd, ploggingTime
    // 플로깅 장소의 플로깅 횟수 + 1 : plogCount + 1
    @Transactional
    public PloggingFinishResponseDto ploggingFinish(Long ploggingUuid, PloggingFinishRequestDto requestDto) {

        Plogging plogging = ploggingRepository.findById(ploggingUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅 기록이 없습니다. ploggingUuid = " + ploggingUuid));

        // ploggingEnd
        LocalDateTime ploggingEnd = LocalDateTime.now();
        plogging.setPloggingEnd(ploggingEnd);

        // ploggingTime
        Duration duration = Duration.between(plogging.getPloggingStart(), plogging.getPloggingEnd());
        Time time = Time.valueOf(LocalTime.ofSecondOfDay(duration.getSeconds()));
        plogging.setPloggingTime(time);

        plogging.ploggingFinish(ploggingEnd, time, requestDto.getPloggingDistance(), requestDto.getPloggingScore());

        // 플로깅 장소의 플로깅 횟수 + 1 : plogCount + 1
        plogging.getPloglocation().setPlogCount(plogging.getPloglocation().getPlogCount() + 1);

        return new PloggingFinishResponseDto(plogging);

    }

    // 플로깅 중 : ploggingScore + 1
    @Transactional
    public Long ploggingScore(Long ploggingUuid) {

        Plogging plogging = ploggingRepository.findById(ploggingUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅 기록이 없습니다. ploggingUuid = " + ploggingUuid));

        plogging.setPloggingScore(plogging.getPloggingScore() + 1);

        return ploggingUuid;
    }

}

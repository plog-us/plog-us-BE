package com.gdscsmwu.earthus.plogus.wastebin.service;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.wastebin.dto.WastebinResponseDto;
import com.gdscsmwu.earthus.plogus.wastebin.repository.WastebinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WastebinService {

    private final WastebinRepository wastebinRepository;

    // wastebin 조회 : binUuid, plogUuid, binAddress, binLatitude, binLongitude
    @Transactional(readOnly = true)
    public List<WastebinResponseDto> viewWastebin(Ploglocation plogUuid) {

        return wastebinRepository.findWastebinsByPloglocation(plogUuid).stream()
                .map(WastebinResponseDto::new)
                .collect(Collectors.toList());

    }

}

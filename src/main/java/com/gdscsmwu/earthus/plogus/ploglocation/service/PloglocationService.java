package com.gdscsmwu.earthus.plogus.ploglocation.service;

import com.gdscsmwu.earthus.plogus.ploglocation.dto.PloglocationResponseDto;
import com.gdscsmwu.earthus.plogus.ploglocation.repository.PloglocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PloglocationService {

    private final PloglocationRepository ploglocationRepository;

    @Transactional(readOnly = true)
    public List<PloglocationResponseDto> viewPloglocation() {

        return ploglocationRepository.findAll().stream()
                .map(PloglocationResponseDto::new)
                .collect(Collectors.toList());

    }

}

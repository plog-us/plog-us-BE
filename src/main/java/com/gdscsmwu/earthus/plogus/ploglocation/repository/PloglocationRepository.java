package com.gdscsmwu.earthus.plogus.ploglocation.repository;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloglocationRepository extends JpaRepository<Ploglocation, Long> {

    Ploglocation findByPlogUuid(Long plogUuid);

}

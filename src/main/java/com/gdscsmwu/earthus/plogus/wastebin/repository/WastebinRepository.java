package com.gdscsmwu.earthus.plogus.wastebin.repository;

import com.gdscsmwu.earthus.plogus.ploglocation.domain.Ploglocation;
import com.gdscsmwu.earthus.plogus.wastebin.domain.Wastebin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WastebinRepository extends JpaRepository<Wastebin, Long> {

    List<Wastebin> findWastebinsByPloglocation(@Param("plogUuid") Ploglocation plogUuid);

}

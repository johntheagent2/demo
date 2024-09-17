package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.dto.TyrePatternRequestDto;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;

import java.util.Optional;

public interface TyrePatternService {
    TyrePattern saveTyrePattern(TyrePatternRequestDto dto);
    Optional<TyrePattern> getTyrePatternById(String id);
    Iterable<TyrePattern> getAllTyrePatterns();
    void deleteTyrePatternById(String id);
}

package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;

import java.util.Optional;

public interface TyrePatternService {
    TyrePattern saveTyrePattern(TyrePattern tyrePattern);
    Optional<TyrePattern> getTyrePatternById(Long id);
    Iterable<TyrePattern> getAllTyrePatterns();
    void deleteTyrePatternById(Long id);
}

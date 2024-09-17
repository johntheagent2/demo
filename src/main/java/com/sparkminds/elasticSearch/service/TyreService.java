package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;

import java.util.Optional;

public interface TyreService {
    TyreEntity saveTyre(TyreRequestDto tyreEntity);

    Optional<TyreEntity> getTyreById(String id);

    Iterable<TyreEntity> getAllTyres();

    void deleteTyreById(String id);
}

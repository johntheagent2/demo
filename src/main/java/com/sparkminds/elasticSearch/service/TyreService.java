package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.entity.TyreEntity;

import java.util.Optional;

public interface TyreService {
    TyreEntity saveTyre(TyreEntity tyreEntity);

    Optional<TyreEntity> getTyreById(Long id);

    Iterable<TyreEntity> getAllTyres();

    void deleteTyreById(Long id);
}

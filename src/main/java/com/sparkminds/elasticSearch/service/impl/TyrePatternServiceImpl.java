package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import com.sparkminds.elasticSearch.repository.TyrePatternRepository;
import com.sparkminds.elasticSearch.service.TyrePatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TyrePatternServiceImpl implements TyrePatternService {

    private final TyrePatternRepository tyrePatternRepository;

    @Autowired
    public TyrePatternServiceImpl(TyrePatternRepository tyrePatternRepository) {
        this.tyrePatternRepository = tyrePatternRepository;
    }

    @Override
    public TyrePattern saveTyrePattern(TyrePattern tyrePattern) {
        return tyrePatternRepository.save(tyrePattern);
    }

    @Override
    public Optional<TyrePattern> getTyrePatternById(Long id) {
        return tyrePatternRepository.findById(id);
    }

    @Override
    public Iterable<TyrePattern> getAllTyrePatterns() {
        return tyrePatternRepository.findAll();
    }

    @Override
    public void deleteTyrePatternById(Long id) {
        tyrePatternRepository.deleteById(id);
    }
}

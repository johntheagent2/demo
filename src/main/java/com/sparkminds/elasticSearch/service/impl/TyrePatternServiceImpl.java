package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.dto.TyrePatternRequestDto;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import com.sparkminds.elasticSearch.repository.TyrePatternRepository;
import com.sparkminds.elasticSearch.service.TyrePatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TyrePatternServiceImpl implements TyrePatternService {

    private final TyrePatternRepository tyrePatternRepository;

    @Override
    public TyrePattern saveTyrePattern(TyrePatternRequestDto dto) {
        TyrePattern tyrePattern = TyrePattern.builder()
                .name(dto.getName())
                .build();
        return tyrePatternRepository.save(tyrePattern);
    }

    @Override
    public Optional<TyrePattern> getTyrePatternById(String id) {
        return tyrePatternRepository.findById(id);
    }

    @Override
    public Iterable<TyrePattern> getAllTyrePatterns() {
        return tyrePatternRepository.findAll();
    }

    @Override
    public void deleteTyrePatternById(String id) {
        tyrePatternRepository.deleteById(id);
    }
}

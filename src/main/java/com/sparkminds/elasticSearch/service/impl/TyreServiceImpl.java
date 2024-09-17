package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.repository.TyreRepository;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TyreServiceImpl implements TyreService {

    private final TyreRepository tyreRepository;
    

    @Override
    public TyreEntity saveTyre(TyreEntity tyreEntity) {
        return tyreRepository.save(tyreEntity);
    }

    @Override
    public Optional<TyreEntity> getTyreById(Long id) {
        return tyreRepository.findById(id);
    }

    @Override
    public Iterable<TyreEntity> getAllTyres() {
        return tyreRepository.findAll();
    }

    @Override
    public void deleteTyreById(Long id) {
        tyreRepository.deleteById(id);
    }
}

package com.sparkminds.elasticSearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.attributes.Brand;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import com.sparkminds.elasticSearch.repository.TyreRepository;
import com.sparkminds.elasticSearch.service.BrandService;
import com.sparkminds.elasticSearch.service.TyrePatternService;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TyreServiceImpl implements TyreService {

    private final TyreRepository tyreRepository;
    private final TyrePatternService tyrePatternService;
    private final BrandService brandService;
    private final ElasticsearchClient client;

    @Override
    public TyreEntity saveTyre(TyreRequestDto dto) {

        Brand brand = brandService.getBrandById(dto.getBrandId()).orElseThrow();
        TyrePattern pattern = tyrePatternService.getTyrePatternById(dto.getPatternId()).orElseThrow();

        TyreEntity tyre = TyreEntity.builder()
                .name(dto.getName())
                .height(dto.getHeight())
                .width(dto.getWidth())
                .rim(dto.getRim())
                .year(dto.getYear())
                .brand(brand)
                .pattern(pattern)
                .build();
        return tyreRepository.save(tyre);
    }

//    public List<TyreEntity> search(SearchRequestDto dto){
//    }

    @Override
    public Optional<TyreEntity> getTyreById(String id) {
        return tyreRepository.findById(id);
    }

    @Override
    public Iterable<TyreEntity> getAllTyres() {
        return tyreRepository.findAll();
    }

    @Override
    public void deleteTyreById(String id) {
        tyreRepository.deleteById(id);
    }
}

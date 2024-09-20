package com.sparkminds.elasticSearch.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.sparkminds.elasticSearch.dto.BrandRequestDto;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/tyre")
@RestController
public class TyreController {

    private final TyreService tyreService;
    private final ElasticSearchService elasticSearchService;

    @PostMapping
    public ResponseEntity<Void> createTyre(@RequestBody TyreRequestDto dto){
        tyreService.saveTyre(dto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public List<TyreEntity> matchAll() throws IOException {
        List<Hit<TyreEntity>> hitList = elasticSearchService.matchAllService().hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<TyreEntity> matchName(@RequestParam String name) throws IOException {
        List<Hit<TyreEntity>> hitList = elasticSearchService.matchService(name).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<TyreEntity> matchWithMultipleFields(@RequestParam Map<String, String> searchCriteria) throws IOException {
        List<Hit<TyreEntity>> hitList = elasticSearchService.matchQuery(searchCriteria).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<TyreEntity> search(@RequestParam String name) throws IOException {
        List<Hit<TyreEntity>> hitList = elasticSearchService.fuzzyQuery("name", name).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

}

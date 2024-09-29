package com.sparkminds.elasticSearch.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/csv")
    public ResponseEntity<Void> importCSV(@RequestBody MultipartFile file){
        tyreService.importTyresFromCSV(file);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public List<TyreDocument> matchAll() throws IOException {
        List<Hit<TyreDocument>> hitList = elasticSearchService.matchAllService().hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<TyreDocument> matchName(@RequestParam String name) throws IOException {
        List<Hit<TyreDocument>> hitList = elasticSearchService.matchService(name).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<TyreDocument> matchWithMultipleFields(@RequestParam Map<String, String> searchCriteria) throws IOException {
        List<Hit<TyreDocument>> hitList = elasticSearchService.matchQuery(searchCriteria).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<TyreDocument> search(@RequestParam String name,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) throws IOException {
        List<Hit<TyreDocument>> hitList = elasticSearchService.fuzzyQuery("name", name, page, size).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @PutMapping("/reindex")
    public ResponseEntity<Void> reindex() throws Exception {
        elasticSearchService.reIndex("tyre", "tyre");
        return ResponseEntity.accepted().build();
    }
}

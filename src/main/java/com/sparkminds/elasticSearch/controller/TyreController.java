package com.sparkminds.elasticSearch.controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.criteria.TyreCriteria;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.service.TyreQueryService;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    private final TyreQueryService tyreQueryService;

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

    //Use Elasticsearch

    @GetMapping("/elastic-filter")
    public List<TyreDocument> matchWithMultipleFields(
            @RequestParam Map<String, String> searchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) throws IOException {

        List<Hit<TyreDocument>> hitList = elasticSearchService.matchQuery(searchCriteria, page, size, sortBy, sortOrder).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }


    @GetMapping("/elastic-search")
    public List<TyreDocument> search(@RequestParam String name,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam String sortBy,
                                     @RequestParam String sortOrder) throws IOException {
        List<Hit<TyreDocument>> hitList = elasticSearchService.fuzzyQuery("name", name, page, size, sortBy, sortOrder).hits().hits();
        return hitList.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @PutMapping("/reindex")
    public ResponseEntity<Void> reindex() throws Exception {
        elasticSearchService.reIndex("tyre", "tyre");
        return ResponseEntity.accepted().build();
    }

    //Use query specification search
    @GetMapping("/query-filter")
    public ResponseEntity<Page<TyreEntity>> querySearch(
            TyreCriteria tyreCriteria,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {

        // Create a Sort object based on the provided sort parameters
        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }

        // Create a new Pageable instance with sorting
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(tyreQueryService.queryTyre(tyreCriteria, pageable));
    }
}

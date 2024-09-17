package com.sparkminds.elasticSearch.controller;

import com.sparkminds.elasticSearch.dto.TyrePatternRequestDto;
import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import com.sparkminds.elasticSearch.service.TyrePatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/tyre-pattern")
@RestController
public class TyrePatternController {

    private final TyrePatternService patternService;

    @PostMapping
    public ResponseEntity<Void> createTyrePattern(@RequestBody TyrePatternRequestDto dto){
        patternService.saveTyrePattern(dto);
        return ResponseEntity.accepted().build();
    }
}

package com.sparkminds.elasticSearch.controller;

import com.sparkminds.elasticSearch.dto.BrandRequestDto;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/tyre")
@RestController
public class TyreController {

    private final TyreService tyreService;

    @PostMapping
    public ResponseEntity<Void> createTyre(@RequestBody TyreRequestDto dto){
        tyreService.saveTyre(dto);
        return ResponseEntity.accepted().build();
    }
}

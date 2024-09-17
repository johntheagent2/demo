package com.sparkminds.elasticSearch.controller;

import com.sparkminds.elasticSearch.dto.BrandRequestDto;
import com.sparkminds.elasticSearch.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/brand")
@RestController
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Void> createBrand(@RequestBody BrandRequestDto dto){
        brandService.saveBrand(dto);
        return ResponseEntity.accepted().build();
    }
}

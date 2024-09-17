package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.dto.BrandRequestDto;
import com.sparkminds.elasticSearch.entity.attributes.Brand;

import java.util.Optional;

public interface BrandService {
    Brand saveBrand(BrandRequestDto dto);
    Optional<Brand> getBrandById(String id);
    Iterable<Brand> getAllBrands();
    void deleteBrandById(String id);
}

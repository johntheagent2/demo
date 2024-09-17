package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.entity.attributes.Brand;

import java.util.Optional;

public interface BrandService {
    Brand saveBrand(Brand brand);
    Optional<Brand> getBrandById(Long id);
    Iterable<Brand> getAllBrands();
    void deleteBrandById(Long id);
}

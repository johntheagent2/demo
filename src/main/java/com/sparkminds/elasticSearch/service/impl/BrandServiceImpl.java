package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.dto.BrandRequestDto;
import com.sparkminds.elasticSearch.entity.attributes.Brand;
import com.sparkminds.elasticSearch.repository.BrandRepository;
import com.sparkminds.elasticSearch.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand saveBrand(BrandRequestDto dto) {
        Brand brand = Brand.builder()
                .name(dto.getName())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> getBrandById(String id) {
        return brandRepository.findById(id);
    }

    @Override
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public void deleteBrandById(String id) {
        brandRepository.deleteById(id);
    }
}

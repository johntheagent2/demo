package com.sparkminds.elasticSearch.repository;

import com.sparkminds.elasticSearch.entity.attributes.Brand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BrandRepository extends ElasticsearchRepository<Brand, Long> {
}

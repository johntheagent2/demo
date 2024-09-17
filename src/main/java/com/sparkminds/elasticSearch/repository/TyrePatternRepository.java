package com.sparkminds.elasticSearch.repository;

import com.sparkminds.elasticSearch.entity.attributes.TyrePattern;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TyrePatternRepository extends ElasticsearchRepository<TyrePattern, String> {
}

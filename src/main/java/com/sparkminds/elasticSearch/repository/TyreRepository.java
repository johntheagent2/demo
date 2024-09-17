package com.sparkminds.elasticSearch.repository;

import com.sparkminds.elasticSearch.entity.TyreEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TyreRepository extends ElasticsearchRepository<TyreEntity, String> {

}

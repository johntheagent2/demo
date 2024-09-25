package com.sparkminds.elasticSearch.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sparkminds.elasticSearch.entity.TyreEntity;

import java.io.IOException;
import java.util.Map;

public interface ElasticSearchService {
    SearchResponse<TyreEntity> matchAllService() throws IOException;

    SearchResponse<TyreEntity> matchService(String value) throws IOException;

    SearchResponse<TyreEntity> matchQuery(Map<String, String> searchCriteria) throws IOException;

    SearchResponse<TyreEntity> fuzzyQuery(String field, String value) throws IOException;

    void reIndex(String baseIndexName, String aliasName) throws Exception;
}

package com.sparkminds.elasticSearch.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;

import java.io.IOException;
import java.util.Map;

public interface ElasticSearchService {
    SearchResponse<TyreDocument> matchAllService() throws IOException;

    SearchResponse<TyreDocument> matchService(String value) throws IOException;

    SearchResponse<TyreDocument> matchQuery(Map<String, String> searchCriteria, int page, int size, String sortBy, String sortOrder) throws IOException;

    SearchResponse<TyreDocument> fuzzyQuery(String field, String value, int page, int size, String sortField, String sortOrder) throws IOException;

    void reIndex(String baseIndexName, String aliasName) throws Exception;

    void createOrUpdateIndexWithAlias() throws IOException;
}

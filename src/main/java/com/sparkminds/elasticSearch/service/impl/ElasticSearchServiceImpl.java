package com.sparkminds.elasticSearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.util.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final ElasticsearchClient client;

    @Override
    public SearchResponse<TyreEntity> matchAllService() throws IOException {
        Supplier<Query> supplier = SearchUtil.supplier();
        SearchResponse<TyreEntity> searchResponse = client.search(s -> s.index("tyre").query(supplier.get()), TyreEntity.class);
        return searchResponse;
    }

    @Override
    public SearchResponse<TyreEntity> matchService(String value) throws IOException {
        Supplier<Query> supplier = SearchUtil.supplierWithNameField(value);
        SearchResponse<TyreEntity> searchResponse = client.search(s -> s.index("tyre").query(supplier.get()), TyreEntity.class);
        return searchResponse;
    }

    @Override
    public SearchResponse<TyreEntity> matchQuery(Map<String, String> searchCriteria) throws IOException {
        Supplier<Query> supplier = SearchUtil.multiFieldQuerySupplier(searchCriteria);
        SearchResponse<TyreEntity> searchResponse = client.search(s -> s.index("tyre").query(supplier.get()), TyreEntity.class);
        return searchResponse;
    }


    @Override
    public SearchResponse<TyreEntity> fuzzyQuery(String field, String value) throws IOException {
        Supplier<Query> supplier = SearchUtil.fuzzySearchSupplier(field, value);
        return createResponse(supplier);
    }

    private SearchResponse<TyreEntity> createResponse(Supplier<Query> supplier) throws IOException {
        return client.search(s -> s.index("tyre").query(supplier.get()), TyreEntity.class);
    }
}

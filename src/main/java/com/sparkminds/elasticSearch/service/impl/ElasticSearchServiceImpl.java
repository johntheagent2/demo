package com.sparkminds.elasticSearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.util.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.AliasData;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.reindex.ReindexRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final ElasticsearchClient client;
    private final ElasticsearchOperations elasticsearchOperations;

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

    @Override
    public void reIndex(String baseIndexName, String aliasName) throws Exception {
        // Step 1: Generate a new index name with timestamp to ensure it's unique
        String newIndexName = baseIndexName + "_" + System.currentTimeMillis();
        System.out.println("Creating new index: " + newIndexName);

        // Step 2: Create the new index
        IndexOperations indexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(newIndexName));
        indexOps.create();
        Document mapping = indexOps.createMapping(TyreEntity.class);
        indexOps.putMapping(mapping);

        System.out.println("New index created: " + newIndexName);

        // Step 3: Reindex data from old index to new index
        String oldIndexName = getIndexNameFromAlias(aliasName);
        ReindexRequest reindexRequest = new ReindexRequest
                .ReindexRequestBuilder(IndexCoordinates.of(oldIndexName), IndexCoordinates.of(newIndexName))
                .build();

        elasticsearchOperations.reindex(reindexRequest);
        System.out.println("Reindexing data from " + oldIndexName + " to " + newIndexName);

        // Step 4: Update alias to point to the new index
        IndexOperations oldIndexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(oldIndexName));
        IndexOperations newIndexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(newIndexName));
        AliasActions aliasActions = new AliasActions();

        // Remove alias from the old index
        oldIndexOps.alias(
                new AliasActions(
                        new AliasAction.Remove(
                                AliasActionParameters
                                        .builder()
                                        .withAliases(aliasName)
                                        .withIndices(oldIndexName)
                                        .build())));

        // Add alias to the new index
        newIndexOps.alias(
                new AliasActions(
                        new AliasAction.Add(
                                AliasActionParameters
                                        .builder()
                                        .withAliases(aliasName)
                                        .withIndices(newIndexName)
                                        .build())));

        System.out.println("Alias " + aliasName + " updated to point to the new index: " + newIndexName);

        // Step 5: Optionally remove the old index
        oldIndexOps.delete();
        System.out.println("Old index deleted: " + oldIndexName);
    }

    private SearchResponse<TyreEntity> createResponse(Supplier<Query> supplier) throws IOException {
        return client.search(s -> s.index("tyre").query(supplier.get()), TyreEntity.class);
    }

    private String getIndexNameFromAlias(String aliasName) throws Exception {
        // Get aliases from all indexes
        Map<String, Set<AliasData>> aliasDataMap = elasticsearchOperations.indexOps(IndexCoordinates.of("*")).getAliases();

        // Iterate through the aliasDataMap to find the index associated with aliasName
        for (Map.Entry<String, Set<AliasData>> entry : aliasDataMap.entrySet()) {
            String indexName = entry.getKey();
            Set<AliasData> aliasDataSet = entry.getValue();

            for (AliasData aliasData : aliasDataSet) {
                if (aliasData.getAlias().equals(aliasName)) {
                    return indexName; // Return the index name associated with the alias
                }
            }
        }

        throw new Exception("Alias not found");
    }
}

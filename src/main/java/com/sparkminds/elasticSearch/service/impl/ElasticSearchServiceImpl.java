package com.sparkminds.elasticSearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import com.sparkminds.elasticSearch.service.ElasticSearchService;
import com.sparkminds.elasticSearch.util.SearchUtil;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Alias;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.AliasData;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.reindex.ReindexRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final ElasticsearchClient client;
    private final ElasticsearchOperations elasticsearchOperations;
    private static final String ALIAS_NAME = "tyre";

    @Override
    public SearchResponse<TyreDocument> matchAllService() throws IOException {
        Supplier<Query> supplier = SearchUtil.supplier();
        SearchResponse<TyreDocument> searchResponse = client.search(s -> s.index("tyre").query(supplier.get()), TyreDocument.class);
        return searchResponse;
    }

    @Override
    public SearchResponse<TyreDocument> matchService(String value) throws IOException {
        Supplier<Query> supplier = SearchUtil.supplierWithNameField(value);
        SearchResponse<TyreDocument> searchResponse = client.search(s -> s.index("tyre").query(supplier.get()), TyreDocument.class);
        return searchResponse;
    }

    @Override
    public SearchResponse<TyreDocument> matchQuery(Map<String, String> searchCriteria, int page, int size, String sortBy, String sortOrder) throws IOException {
        Supplier<Query> supplier = SearchUtil.multiFieldQuerySupplier(searchCriteria);
        return createResponse(supplier, page, size, sortBy, sortOrder);
    }


    @Override
    public SearchResponse<TyreDocument> fuzzyQuery(String field, String value, int page, int size, String sortField,
                                                   String sortOrder) throws IOException {
        Supplier<Query> supplier = SearchUtil.fuzzySearchSupplier(field, value);
        return createResponse(supplier, page, size, sortField, sortOrder);
    }

    @Override
    public void reIndex(String baseIndexName, String aliasName) throws Exception {
        // Step 1: Generate a new index name with timestamp to ensure it's unique
        String newIndexName = baseIndexName + "_" + System.currentTimeMillis();
        System.out.println("Creating new index: " + newIndexName);

        // Step 2: Create the new index
        IndexOperations indexOps = elasticsearchOperations.indexOps(IndexCoordinates.of(newIndexName));
        indexOps.create();
        Document mapping = indexOps.createMapping(TyreDocument.class);
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

    public void createOrUpdateIndexWithAlias() {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(IndexCoordinates.of(ALIAS_NAME));

        // Check if alias already exists
        boolean aliasExists = indexOperations.exists();
        if (aliasExists) {
            // Get all indices associated with the alias
            Map<String, Set<AliasData>> aliasInfo = elasticsearchOperations.indexOps(IndexCoordinates.of(ALIAS_NAME)).getAliases();

            // If alias exists and points to an index, skip creation
            if (!aliasInfo.isEmpty()) {
                String currentIndex = aliasInfo.keySet().iterator().next();
                System.out.println("Alias " + ALIAS_NAME + " already points to index: " + currentIndex);
                return; // Alias already points to an index, no need to create a new one
            }
        }

        // Create new index with a timestamp if no alias or index exists
        String newIndexName = ALIAS_NAME + "_" + System.currentTimeMillis();
        IndexOperations newIndex = elasticsearchOperations.indexOps(IndexCoordinates.of(newIndexName));
        newIndex.create();
        newIndex.putMapping(indexOperations.createMapping(TyreDocument.class));

        // Create alias pointing to the new index
        newIndex.alias(
                new AliasActions(
                        new AliasAction.Add(
                                AliasActionParameters
                                        .builder()
                                        .withAliases(ALIAS_NAME)
                                        .withIndices(newIndexName)
                                        .build())));

        System.out.println("Created new index: " + newIndexName + " and pointed alias " + ALIAS_NAME + " to it.");
    }


    private SearchResponse<TyreDocument> createResponse(Supplier<Query> supplier, int page, int size, String sortField, String sortOrder) throws IOException {
        // Use "year.keyword" instead of "year"

        if(sortField != null && !sortField.isEmpty() && sortOrder != null && !sortOrder.isEmpty()){
            String sortFieldAdjusted = sortField.equals("year") ? "year.keyword" : sortField;
            return client.search(s -> s
                            .index("tyre")
                            .query(supplier.get())
                            .from(page * size)
                            .size(size)
                            .sort(s1 -> s1.field(f -> f.field(sortFieldAdjusted)
                                    .order(sortOrder.equalsIgnoreCase("asc")
                                            ? SortOrder.Asc
                                            : SortOrder.Desc))), // Apply sorting if sortBy and sortOrder are provided
                    TyreDocument.class
            );
        }else{
            return client.search(s -> s
                            .index("tyre")
                            .query(supplier.get())
                            .from(page * size)
                            .size(size),
                    TyreDocument.class
            );
        }
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

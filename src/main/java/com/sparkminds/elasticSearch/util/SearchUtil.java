package com.sparkminds.elasticSearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.Map;
import java.util.function.Supplier;

public class SearchUtil {

    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static Supplier<Query> supplierWithNameField(String name){
        Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQuery(name)));
        return supplier;
    }

    public static Supplier<Query> multiFieldQuerySupplier(Map<String, String> searchCriteria) {
        Supplier<Query> supplier = () -> Query.of(q ->
                q.bool(b -> {
                    for (Map.Entry<String, String> entry : searchCriteria.entrySet()) {
                        b.must(m -> m.match(mt -> mt.field(entry.getKey()).query(entry.getValue())));
                    }
                    return b;
                })
        );
        return supplier;
    }

    public static Supplier<Query> fuzzySearchSupplier(String field, String value) {
        Supplier<Query> supplier = () -> Query.of(q ->
                q.match(m -> m.field(field).query(value).fuzziness("AUTO")) // AUTO or a specific fuzziness level
        );
        return supplier;
    }


    public static MatchAllQuery matchAllQuery(){
        MatchAllQuery matchAllQuery = new MatchAllQuery.Builder().build();
        return matchAllQuery;
    }

    public static MatchQuery matchQuery(String fieldValue){
        MatchQuery matchAllQuery = new MatchQuery.Builder()
                .field("name")
                .query(fieldValue)
                .build();
        return matchAllQuery;
    }
}

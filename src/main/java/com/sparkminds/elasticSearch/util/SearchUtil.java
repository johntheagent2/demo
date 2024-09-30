package com.sparkminds.elasticSearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;

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
        return () -> Query.of(q ->
                q.bool(b -> {
                    // Retrieve values from the map and check for null before adding to the query
                    if (searchCriteria.containsKey("name")) {
                        b.must(m -> m.match(mt -> mt.field("name").query(searchCriteria.get("name"))));
                    }
                    if (searchCriteria.containsKey("pattern")) {
                        b.must(m -> m.match(mt -> mt.field("pattern").query(searchCriteria.get("pattern"))));
                    }
                    if (searchCriteria.containsKey("width")) {
                        b.must(m -> m.match(mt -> mt.field("width").query(searchCriteria.get("width"))));
                    }
                    if (searchCriteria.containsKey("height")) {
                        b.must(m -> m.match(mt -> mt.field("height").query(searchCriteria.get("height"))));
                    }
                    if (searchCriteria.containsKey("rim")) {
                        b.must(m -> m.match(mt -> mt.field("rim").query(searchCriteria.get("rim"))));
                    }
                    if (searchCriteria.containsKey("loadIndex")) {
                        b.must(m -> m.match(mt -> mt.field("loadIndex").query(searchCriteria.get("loadIndex"))));
                    }
                    if (searchCriteria.containsKey("year")) {
                        b.must(m -> m.match(mt -> mt.field("year").query(searchCriteria.get("year"))));
                    }
                    if (searchCriteria.containsKey("brand")) {
                        b.must(m -> m.match(mt -> mt.field("brand").query(searchCriteria.get("brand"))));
                    }
                    return b;
                })
        );
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

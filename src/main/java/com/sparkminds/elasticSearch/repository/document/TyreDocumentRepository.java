package com.sparkminds.elasticSearch.repository.document;

import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TyreDocumentRepository extends ElasticsearchRepository<TyreDocument, String> {
}

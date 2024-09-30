package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.criteria.TyreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TyreQueryService {
    Page<TyreEntity> queryTyre(TyreCriteria tyreCriteria, Pageable pageable);
}

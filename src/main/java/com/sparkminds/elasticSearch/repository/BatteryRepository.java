package com.sparkminds.elasticSearch.repository;

import com.sparkminds.elasticSearch.entity.BatteryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BatteryRepository extends ElasticsearchRepository<BatteryEntity, String> {
}

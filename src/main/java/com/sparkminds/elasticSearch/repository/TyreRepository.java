package com.sparkminds.elasticSearch.repository;

import com.sparkminds.elasticSearch.entity.TyreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TyreRepository extends JpaRepository<TyreEntity, Long>, JpaSpecificationExecutor<TyreEntity> {

}

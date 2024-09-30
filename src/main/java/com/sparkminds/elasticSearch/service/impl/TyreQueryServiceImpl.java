package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.TyreEntity_;
import com.sparkminds.elasticSearch.entity.criteria.TyreCriteria;
import com.sparkminds.elasticSearch.repository.TyreRepository;
import com.sparkminds.elasticSearch.service.TyreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.jhipster.service.QueryService;

@Service
@RequiredArgsConstructor
public class TyreQueryServiceImpl extends QueryService<TyreEntity> implements TyreQueryService {

    private final TyreRepository tyreRepository;

    @Override
    public Page<TyreEntity> queryTyre(TyreCriteria tyreCriteria, Pageable pageable){
        Specification<TyreEntity> specification = createSpecification(tyreCriteria);
        return tyreRepository.findAll(specification, pageable);
    }

    private Specification<TyreEntity> createSpecification(TyreCriteria criteria) {
        Specification<TyreEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TyreEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TyreEntity_.name));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildSpecification(criteria.getWidth(), TyreEntity_.width));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), TyreEntity_.height));
            }
            if (criteria.getRim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRim(), TyreEntity_.rim));
            }
            if (criteria.getLoadIndex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoadIndex(), TyreEntity_.loadIndex));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), TyreEntity_.year));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), TyreEntity_.brand));
            }
            if (criteria.getPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPattern(), TyreEntity_.pattern));
            }
        }
        return specification;
    }
}

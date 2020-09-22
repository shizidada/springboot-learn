package org.moose.operator.repository;

import org.moose.operator.model.entity.PoetryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface PoetryRepository extends ElasticsearchRepository<PoetryEntity, Integer> {
}

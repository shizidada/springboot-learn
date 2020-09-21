package org.moose.operator.repository;

import org.moose.operator.es.entity.ExcelInfoEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface ExcelInfoRepository extends ElasticsearchRepository<ExcelInfoEntity, Integer> {
}

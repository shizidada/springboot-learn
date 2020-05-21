package org.excel.operator.es.repository;

import org.excel.operator.es.entity.ExcelInfoEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface ExcelInfoRepository extends ElasticsearchRepository<ExcelInfoEntity, Integer> {
}

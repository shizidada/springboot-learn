package org.excel.operator.es.repository;

import org.excel.operator.entity.ExcelInfoDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface ExcelInfoRepository extends ElasticsearchRepository<ExcelInfoDO, Integer> {
}

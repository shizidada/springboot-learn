package org.excel.operator.es.repository;

import org.excel.operator.entity.ImportExcelDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface ImportExcelRepository extends ElasticsearchRepository<ImportExcelDO, Integer> {
}

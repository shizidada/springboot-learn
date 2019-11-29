package org.excel.operator.es.repository;

import org.excel.operator.es.document.ImportExcelDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author taohua
 */
public interface ImportExcelRepository extends ElasticsearchRepository<ImportExcelDoc, Integer> {
}

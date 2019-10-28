package org.excel.operator.service;

import org.excel.operator.entity.ImportExcelDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:09
 * @see org.excel.operator.service
 */
public interface ImportExcelService {

  int addImportExcelRecord(ImportExcelDO importExcelDO);
}

package org.excel.operator.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.excel.operator.entity.ImportExcelDO;
import org.excel.operator.mapper.ImportExcelMapper;
import org.excel.operator.service.ImportExcelService;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:10
 * @see org.excel.operator.service.impl
 */
public class ImportExcelServiceImpl implements ImportExcelService {

  @Resource
  private ImportExcelMapper importExcelMapper;

  @Override public ImportExcelDO selectByPrimaryKey(Long id) {
    return importExcelMapper.selectByPrimaryKey(id);
  }

  @Override public int addImportExcelRecord(ImportExcelDO importExcelDO) {
    return importExcelMapper.addImportExcelRecord(importExcelDO);
  }

  @Override public int addImportExcelRecordBatch(List<ImportExcelDO> importExcelDO) {
    return importExcelMapper.addImportExcelInfoBatch(importExcelDO);
  }
}

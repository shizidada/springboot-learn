package org.excel.operator.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.excel.operator.entity.ImportExcelDO;
import org.excel.operator.mapper.ImportExcelMapper;
import org.excel.operator.service.ImportExcelService;
import org.springframework.stereotype.Service;

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
@Service
public class ImportExcelServiceImpl implements ImportExcelService {

  @Resource
  private ImportExcelMapper importExcelMapper;

  @Override public List<ImportExcelDO> selectAll() {
    return importExcelMapper.selectAll();
  }

  @Override public ImportExcelDO selectByPrimaryKey(Long id) {
    return importExcelMapper.selectByPrimaryKey(id);
  }

  @Override public ImportExcelDO selectByImportExcel(ImportExcelDO importExcelDO) {
    return importExcelMapper.selectByImportExcel(importExcelDO);
  }

  @Override public int addImportExcelRecord(ImportExcelDO importExcelDO) {
    return importExcelMapper.addImportExcelRecord(importExcelDO);
  }

  @Override public int addImportExcelRecordBatch(List<ImportExcelDO> importExcelDO) {
    return importExcelMapper.addImportExcelRecordBatch(importExcelDO);
  }

  @Override public List<ImportExcelDO> exportSameReceiverAndPhoneAndAddress() {
    return importExcelMapper.selectSameReceiverAndPhoneAndAddress();
  }

  @Override public List<ImportExcelDO> exportDiffReceiverAndPhoneAndAddress() {
    return importExcelMapper.selectDiffReceiverAndPhoneAndAddress();
  }
}

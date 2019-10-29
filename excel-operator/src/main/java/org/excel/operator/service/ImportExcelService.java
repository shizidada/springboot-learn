package org.excel.operator.service;

import java.util.List;
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

  /**
   * 查询所有数据
   *
   * @return List<ImportExcelDO>
   */
  List<ImportExcelDO> selectAll();

  /**
   * 根据主键查询
   *
   * @param id 主键
   * @return ImportExcelDO
   */
  ImportExcelDO selectByPrimaryKey(Long id);

  /**
   * 根据 importExcelDO 查询
   *
   * @param importExcelDO 实体
   * @return ImportExcelDO
   */
  ImportExcelDO selectByImportExcel(ImportExcelDO importExcelDO);

  /**
   * 添加一条 excel 数据
   *
   * @param importExcelDO 实体
   * @return 添加成功
   */
  int addImportExcelRecord(ImportExcelDO importExcelDO);

  /**
   * 批量添加 excel 数据
   *
   * @param importExcelDO 实体集合
   * @return 添加成功
   */
  int addImportExcelRecordBatch(List<ImportExcelDO> importExcelDO);

  /**
   * 批量导出相同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelDO> exportSameReceiverAndPhoneAndAddress();

  /**
   * 批量导出不同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelDO> exportDiffReceiverAndPhoneAndAddress();
}

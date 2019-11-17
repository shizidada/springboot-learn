package org.excel.operator.service;

import java.util.List;
import java.util.Map;
import org.excel.operator.service.model.ImportExcelModel;

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
   * 根据对应条件查询数据
   *
   * @param importExcelModel 搜索参数
   * @return Map<String ,   Object>
   */
  Map<String, Object> selectAll(ImportExcelModel importExcelModel);

  /**
   * 根据主键查询
   *
   * @param id 主键
   * @return ImportExcelDO
   */
  ImportExcelModel selectByPrimaryKey(Long id);

  /**
   * 根据 importExcelDO 查询
   *
   * @param model 实体
   * @return ImportExcelDO
   */
  ImportExcelModel selectByImportExcel(ImportExcelModel model);

  /**
   * 添加一条 excel 数据
   *
   * @param model 实体
   * @return 添加成功
   */
  int addImportExcelRecord(ImportExcelModel model);

  /**
   * 批量添加 excel 数据
   *
   * @param models 实体集合
   * @return 添加成功
   */
  int addImportExcelRecordBatch(List<ImportExcelModel> models);

  /**
   * 批量导出相同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelModel> exportSameReceiverAndPhoneAndAddress();

  /**
   * 批量导出不同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelModel> exportDiffReceiverAndPhoneAndAddress();
}

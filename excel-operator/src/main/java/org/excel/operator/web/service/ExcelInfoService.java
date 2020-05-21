package org.excel.operator.web.service;

import java.util.List;
import java.util.Map;
import org.excel.operator.web.service.model.ImportExcelModel;
import org.excel.operator.web.service.model.UploadInfoModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:09
 * @see org.excel.operator.web.service
 */
public interface ExcelInfoService {

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
   * @return ExcelInfoDO
   */
  ImportExcelModel selectByPrimaryKey(Long id);

  /**
   * 根据 importExcelDO 查询
   *
   * @param model 实体
   * @return ExcelInfoDO
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
   * @param file 上传文件
   * @param uploadInfoModel 实体集合
   * @return 添加成功
   */
  int addBatchImportExcelRecord(MultipartFile file, UploadInfoModel uploadInfoModel);

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

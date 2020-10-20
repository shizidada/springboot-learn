package org.moose.operator.web.service;

import java.util.List;
import java.util.Map;
import org.moose.operator.model.dto.ImportExcelDTO;
import org.moose.operator.model.dto.ExcelUploadInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:09
 * @see org.moose.operator.web.service
 */
public interface ExcelInfoService {

  /**
   * 根据对应条件查询数据
   *
   * @param importExcelDTO 搜索参数
   * @return Map<String, Object>
   */
  Map<String, Object> selectAll(ImportExcelDTO importExcelDTO);

  /**
   * 根据主键查询
   *
   * @param id 主键
   * @return ExcelInfoDO
   */
  ImportExcelDTO selectByPrimaryKey(Long id);

  /**
   * 根据 importExcelDO 查询
   *
   * @param model 实体
   * @return ExcelInfoDO
   */
  ImportExcelDTO selectByImportExcel(ImportExcelDTO model);

  /**
   * 添加一条 excel 数据
   *
   * @param model 实体
   * @return 添加成功
   */
  int addImportExcelRecord(ImportExcelDTO model);

  /**
   * 批量添加 excel 数据
   *
   * @param file          上传文件
   * @param excelUploadInfoDTO 实体集合
   * @return 添加成功
   */
  int addBatchImportExcelRecord(MultipartFile file, ExcelUploadInfoDTO excelUploadInfoDTO);

  /**
   * 批量导出相同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelDTO> exportSameReceiverAndPhoneAndAddress();

  /**
   * 批量导出不同 excel 数据 Receiver Phone Address
   *
   * @return 导出成功
   */
  List<ImportExcelDTO> exportDiffReceiverAndPhoneAndAddress();
}

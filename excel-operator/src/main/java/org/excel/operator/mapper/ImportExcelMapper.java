package org.excel.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.excel.operator.entity.ImportExcelDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:04
 * @see org.excel.operator.mapper
 */
@Mapper
public interface ImportExcelMapper {
  /**
   * 查询所有数据
   *
   * @param importExcelDO 查询条件
   * @return List<ImportExcelDO>
   */
  List<ImportExcelDO> selectAll(ImportExcelDO importExcelDO);

  /**
   * 根据主键查询
   *
   * @param id 主键
   * @return ImportExcelDO
   */
  ImportExcelDO selectByPrimaryKey(Long id);

  /**
   * 根据 ImportExcelDO 查询
   *
   * @param importExcelDO 实体
   * @return ImportExcelDO
   */
  ImportExcelDO selectByImportExcel(ImportExcelDO importExcelDO);

  /**
   * 批量添加 excel record
   *
   * @return 是否添加成功
   */
  int addImportExcelRecordBatch(@Param("excelDOList") List<ImportExcelDO> importExcelDOList);

  /**
   * 添加一条 excel record
   *
   * @return 是否添加成功
   */
  int addImportExcelRecord(ImportExcelDO importExcelDO);

  /**
   * 查询相同 Receiver Phone Address
   *
   * @return ImportExcelDO
   */
  List<ImportExcelDO> selectSameReceiverAndPhoneAndAddress();

  /**
   * 查询不同 Receiver Phone Address
   *
   * @return List<ImportExcelDO>
   */
  List<ImportExcelDO> selectDiffReceiverAndPhoneAndAddress();
}


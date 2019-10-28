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
   * 根据主键查询
   *
   * @param id 主键
   * @return ImportExcelDO
   */
  ImportExcelDO selectByPrimaryKey(Long id);

  /**
   * 根据主键查询
   *
   * @param excelDOList
   * @return 是否添加成功
   */
  int addImportExcelInfoBatch(@Param("excelDOList") List<ImportExcelDO> excelDOList);

  /**
   * 根据主键查询
   *
   * @param importExcelDO
   * @return 是否添加成功
   */
  int addImportExcelRecord(ImportExcelDO importExcelDO);

  /**
   * 根据主键查询
   *
   * @return ImportExcelDO
   */
  List<ImportExcelDO> selectSameReceiverAndPhoneAndAddress();

  /**
   * 根据主键查询
   *
   * @return List<ImportExcelDO>
   */
  List<ImportExcelDO> selectDiffReceiverAndPhoneAndAddress();
}


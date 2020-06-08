package org.excel.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.excel.operator.model.domain.ExcelInfoDO;

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
public interface ExcelInfoMapper {
  /**
   * 查询所有数据
   *
   * @param excelInfoDO 查询条件
   * @return List<ExcelInfoDO>
   */
  List<ExcelInfoDO> selectAll(ExcelInfoDO excelInfoDO);

  /**
   * 根据主键查询
   *
   * @param id 主键
   * @return ExcelInfoDO
   */
  ExcelInfoDO selectByPrimaryKey(Long id);

  /**
   * 根据 ExcelInfoDO 查询
   *
   * @param excelInfoDO 实体
   * @return ExcelInfoDO
   */
  ExcelInfoDO selectByImportExcel(ExcelInfoDO excelInfoDO);

  /**
   * 批量添加 excel record
   *
   * @return 是否添加成功
   */
  int addImportExcelRecordBatch(@Param("excelDOList") List<ExcelInfoDO> list);

  /**
   * 添加一条 excel record
   *
   * @return 是否添加成功
   */
  int addImportExcelRecord(ExcelInfoDO excelInfoDO);

  /**
   * 查询相同 Receiver Phone Address
   *
   * @return ExcelInfoDO
   */
  List<ExcelInfoDO> selectSameReceiverAndPhoneAndAddress();

  /**
   * 查询不同 Receiver Phone Address
   *
   * @return List<ExcelInfoDO>
   */
  List<ExcelInfoDO> selectDiffReceiverAndPhoneAndAddress();
}


package org.excel.operator.mapper;

import java.util.List;
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
public interface ImportExcelMapper {

  ImportExcelDO selectByPrimaryKey(Long id);

  int addImportExcelInfoBatch(@Param("excelInfos") List<ImportExcelDO> excelInfos);

  List<ImportExcelDO> selectSameReceiverAndPhoneAndAddress();

  List<ImportExcelDO> selectDiffReceiverAndPhoneAndAddress();

}


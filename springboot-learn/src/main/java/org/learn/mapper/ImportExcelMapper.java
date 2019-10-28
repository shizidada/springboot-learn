package org.learn.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.learn.entity.ImportExcelInfoDO;

public interface ImportExcelMapper {

  List<ImportExcelInfoDO> selectByPrimaryKey(Long id);

  int addImportExcelInfoBatch(@Param("excelInfos") List<ImportExcelInfoDO> excelInfos);

  List<ImportExcelInfoDO> selectSameReceiverAndPhoneAndAddress();

  List<ImportExcelInfoDO> selectDiffReceiverAndPhoneAndAddress();

}

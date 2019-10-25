package org.learn.service;

import java.util.List;
import org.learn.entity.ImportExcelInfoDO;

public interface ImportExcelFileService {

  int addImportExcelRecord(List<ImportExcelInfoDO> list);

  List<ImportExcelInfoDO> selectSameReceiverAndPhoneAndAddress();

  List<ImportExcelInfoDO> selectDiffReceiverAndPhoneAndAddress();

}

package org.learn.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.learn.entity.ImportExcelInfoDO;
import org.learn.mapper.ImportExcelInfoMapper;
import org.learn.service.ImportExcelFileService;
import org.springframework.stereotype.Service;

@Service
public class ImportExcelFileServiceImpl implements ImportExcelFileService {

  @Resource ImportExcelInfoMapper importExcelInfoMapper;

  @Override public int addImportExcelRecord(List<ImportExcelInfoDO> list) {
    return importExcelInfoMapper.addImportExcelInfoBatch(list);
  }

  @Override public List<ImportExcelInfoDO> selectSameReceiverAndPhoneAndAddress() {
    return importExcelInfoMapper.selectSameReceiverAndPhoneAndAddress();
  }

  @Override public List<ImportExcelInfoDO> selectDiffReceiverAndPhoneAndAddress() {
    return importExcelInfoMapper.selectDiffReceiverAndPhoneAndAddress();
  }
}

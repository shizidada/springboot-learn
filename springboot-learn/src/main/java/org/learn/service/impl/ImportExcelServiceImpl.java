package org.learn.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.learn.entity.ImportExcelInfoDO;
import org.learn.mapper.ImportExcelMapper;
import org.learn.service.ImportExcelService;
import org.springframework.stereotype.Service;

@Service
public class ImportExcelServiceImpl implements ImportExcelService {

  @Resource ImportExcelMapper importExcelInfoMapper;

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

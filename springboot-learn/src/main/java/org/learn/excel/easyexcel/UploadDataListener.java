package org.learn.excel.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import org.learn.entity.ImportExcelInfoDO;
import org.learn.service.ImportExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author shizi
 */
public class UploadDataListener extends AnalysisEventListener<ExcelInfo> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);
  /**
   * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
   */
  private static final int BATCH_COUNT = 5;
  List<ExcelInfo> list = new ArrayList<ExcelInfo>();

  private ImportExcelService importExcelFileService;

  public UploadDataListener(ImportExcelService importExcelFileService) {
    this.importExcelFileService = importExcelFileService;
  }

  @Override
  public void invoke(ExcelInfo data, AnalysisContext context) {
    LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
    data.getAddress().trim();
    data.getPhone().trim();
    data.getReceiver().trim();
    list.add(data);
    if (list.size() >= BATCH_COUNT) {
      saveData();
      list.clear();
    }
  }

  @Override public void onException(Exception exception, AnalysisContext context) throws Exception {
    super.onException(exception, context);
    LOGGER.error(exception.getMessage());
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    LOGGER.info("所有数据解析完成！");
  }

  /**
   * 加上存储数据库
   */
  private void saveData() {
    List<ImportExcelInfoDO> importExcelInfoDOS = new ArrayList<>();
    for (ExcelInfo info : list) {
      ImportExcelInfoDO infoDO = new ImportExcelInfoDO();
      BeanUtils.copyProperties(info, infoDO);
      importExcelInfoDOS.add(infoDO);
    }
    importExcelFileService.addImportExcelRecord(importExcelInfoDOS);
    //LOGGER.info("{}条数据，开始存储数据库！", list.size());
    LOGGER.info("存储数据库成功！");
  }
}

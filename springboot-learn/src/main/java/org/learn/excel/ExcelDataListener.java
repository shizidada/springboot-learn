package org.learn.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shizi
 */
public class ExcelDataListener extends AnalysisEventListener<ExcelInfo> {

  private static final Logger logger = LoggerFactory.getLogger(ExcelDataListener.class);

  private static final String PREFIX_FILE_PATH =
      "D:\\Code\\github\\java\\spring-learn\\springboot-learn\\src\\main\\java\\org\\learn\\excel\\";

  /**
   * 每隔5条存储数据库，实际使用中可以 3000 条，然后清理 list ，方便内存回收
   */
  private static final int BATCH_COUNT = 5;
  private List<ExcelInfo> allExcelInfo = new ArrayList<>();

  /**
   * 临时数据
   */
  private List<String> tempList = new ArrayList<>();

  @Override public void invoke(ExcelInfo excelInfo, AnalysisContext analysisContext) {
    // logger.info("解析到一条数据:{}", JSON.toJSONString(excelInfo));
    allExcelInfo.add(excelInfo);
    //if (list.size() >= BATCH_COUNT) {
    //  saveData();
    //  list.clear();
    //}
  }

  @Override public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    this.saveFilterExcelInfo();
    logger.info("所有数据解析完成！");
  }

  /**
   * 加上存储数据库
   */
  private void saveFilterExcelInfo() {

    /**
     * 相同数据
     */
    List<ExcelInfo> sameInfoList = new ArrayList<>();

    /**
     * 不同数据
     */
    List<ExcelInfo> differentInfoList = new ArrayList<>();

    for (int i = 0; i < allExcelInfo.size() - 1; i++) {
      for (int j = allExcelInfo.size() - 1; j > i; j--) {
        ExcelInfo current = allExcelInfo.get(i);
        ExcelInfo next = allExcelInfo.get(j);

        if (!next.getReceiver().equals(current.getReceiver()) && !next.getPhone()
            .equals(current.getPhone()) && !next.getAddress().equals(current.getAddress())) {
          differentInfoList.add(next);
        } else {
          sameInfoList.add(next);
        }
      }
    }

    //this.writeToExcelFile(PREFIX_FILE_PATH + "different.xlsx", differentInfoList);

    //this.writeToExcelFile(PREFIX_FILE_PATH + "same.xlsx", sameInfoList);
  }

  private void writeToExcelFile(String fileName, List<ExcelInfo> data) {
    EasyExcel.write(fileName)
        .withTemplate(PREFIX_FILE_PATH + "template2.xlsx")
        .sheet("Sheet1")
        .doWrite(data);
  }
}

//for (int i = 0; i < list.size(); i++) {
//  ExcelInfo excelInfo = list.get(i);
//  String info = excelInfo.getReceiver() + excelInfo.getPhone() + excelInfo.getAddress();
//  if (!tempList.contains(info)) {
//    differentInfoList.add(excelInfo);
//    tempList.add(info);
//  } else {
//    sameInfoList.add(excelInfo);
//  }
//}

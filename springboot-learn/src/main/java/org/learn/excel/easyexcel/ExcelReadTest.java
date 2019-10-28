package org.learn.excel.easyexcel;

import com.alibaba.excel.EasyExcel;
import java.io.File;
import org.learn.excel.easyexcel.util.ExcelFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shizi
 */
public class ExcelReadTest {
  private static final Logger logger = LoggerFactory.getLogger(ExcelReadTest.class);

  public static void main(String[] args) {
    String fileName = ExcelFileUtil.getPath() + "excel" + File.separator + "anqin.xlsx";
    EasyExcel.read(fileName, ExcelInfo.class, new ExcelDataListener()).build().read();
  }
}

package org.learn.excel;

import com.alibaba.excel.EasyExcel;
import java.io.File;
import org.learn.excel.util.ExcelFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shizi
 */
public class ExcelReadTest {
  private static final Logger logger = LoggerFactory.getLogger(ExcelReadTest.class);

  public static void main(String[] args) {
    String fileName = ExcelFileUtil.getPath() + "excel" + File.separator + "天蓬安庆.xlsx";
    EasyExcel.read(fileName, ExcelInfo.class, new ExcelDataListener()).build().read();
  }
}

package org.excel.operator.poi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 12:33
 * @see org.excel.operator.poi
 */
public class XSSFOperatorTest {
  private static final Logger logger = LoggerFactory.getLogger(XSSFOperatorTest.class);

  public static void main(String[] args) {
    String filePath = XSSFOperator.class.getResource("/").getPath() + "/excel/tianpeng.xlsx";
    XSSFOperator xssfOperator = new XSSFOperator();
    //xssfOperator.importExcelFile(filePath);
    xssfOperator.exportExcelFile();
  }
}

package org.excel.operator.service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/12/9 22:55
 * @see org.excel.operator.service
 */
public interface ExcelExportService {

  /**
   * 根据类型下载 excel
   *
   * @param response HttpServletResponse
   * @param type 类型
   */
  void downLoadExportExcelFile(HttpServletResponse response, String type);
}

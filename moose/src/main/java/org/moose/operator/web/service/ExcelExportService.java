package org.moose.operator.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/12/9 22:55
 * @see org.moose.operator.web.service
 */
public interface ExcelExportService {

  /**
   * 根据类型下载 excel
   *
   * @param response HttpServletResponse
   * @param request  HttpServletResponse
   */
  void downLoadExportExcelFile(HttpServletResponse response, HttpServletRequest request);
}

package org.excel.operator.web.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.web.service.impl.ExcelExportServiceImpl;
import org.excel.operator.web.service.impl.ExcelInfoServiceImpl;
import org.excel.operator.web.service.model.ImportExcelModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.excel.operator.web.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ExcelExportController {

  @Resource
  private ExcelInfoServiceImpl excelInfoService;

  @Resource
  private ExcelExportServiceImpl excelExportService;

  @GetMapping(value = "/list")
  public ResponseResult<Map<String, Object>> list(ImportExcelModel importExcelModel) {
    Map<String, Object> map = excelInfoService.selectAll(importExcelModel);
    return new ResponseResult<>(map);
  }

  @RequestMapping(value = "/export")
  public void exportFile(HttpServletResponse response, HttpServletRequest request) {
    String type = request.getParameter("type");
    excelExportService.downLoadExportExcelFile(response, type);
  }
}

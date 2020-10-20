package org.moose.operator.web.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.moose.operator.common.api.R;
import org.moose.operator.model.dto.ImportExcelDTO;
import org.moose.operator.web.service.impl.ExcelExportServiceImpl;
import org.moose.operator.web.service.impl.ExcelInfoServiceImpl;
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
 * @see org.moose.operator.web.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ExcelExportController {

  @Resource
  private ExcelInfoServiceImpl excelInfoService;

  @Resource
  private ExcelExportServiceImpl excelExportService;

  @GetMapping(value = "/list")
  public R<Map<String, Object>> list(ImportExcelDTO importExcelDTO) {
    return R.ok(excelInfoService.selectAll(importExcelDTO));
  }

  @RequestMapping(value = "/export")
  public void exportFile(HttpServletResponse response, HttpServletRequest request) {
    excelExportService.downLoadExportExcelFile(response, request);
  }
}

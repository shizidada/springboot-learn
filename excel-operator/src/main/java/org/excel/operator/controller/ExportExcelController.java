package org.excel.operator.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.poi.XSSFOperator;
import org.excel.operator.service.impl.ImportExcelServiceImpl;
import org.excel.operator.service.model.ImportExcelModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @see org.excel.operator.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ExportExcelController {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

  private static final String EXCEL_SAME = "same";

  private static final String EXCEL_DIFF = "diff";

  @Resource
  private ImportExcelServiceImpl importExcelService;

  @GetMapping(value = "/list")
  public ResponseResult list(ImportExcelModel importExcelModel) {
    Map<String, Object> map = importExcelService.selectAll(importExcelModel);
    return ResponseResult.success(map);
  }

  @RequestMapping(value = "/export")
  public void exportFile(HttpServletResponse response, HttpServletRequest request) {
    String type = request.getParameter("type");
    downLoadExportExcelFile(response, type);
  }

  private void downLoadExportExcelFile(HttpServletResponse response, String type) {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    List<ImportExcelModel> exportList = new ArrayList<>();
    try {
      String fileName = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8");
      response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

      if (EXCEL_SAME.equals(type)) {
        exportList = importExcelService.exportSameReceiverAndPhoneAndAddress();
      } else if (EXCEL_DIFF.equals(type)) {
        exportList = importExcelService.exportDiffReceiverAndPhoneAndAddress();
      }
      ServletOutputStream outputStream = response.getOutputStream();

      XSSFOperator xssfOperator = new XSSFOperator();
      xssfOperator.exportExcelFile(exportList, outputStream);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new BusinessException(ResponseCode.EXCEL_EXPORT_FAIL.getMessage(),
          ResponseCode.EXCEL_EXPORT_FAIL.getCode());
    }
  }
}
